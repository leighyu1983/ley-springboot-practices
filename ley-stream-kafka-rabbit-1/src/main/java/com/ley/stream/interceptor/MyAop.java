package com.ley.stream.interceptor;


import com.ley.annotation.MqKey;
import com.ley.configuration.pojo.MqTypeEnum;
import com.ley.constant.MqDictionary;
import com.ley.stream.storage.StorageProvider;
import com.ley.stream.storage.pojo.ReceivedMessage;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Date;

import static com.ley.constant.Constant.MQ_TRACE_ID;


/**
 *
 * status:
 * 		0 - not proceeded
 * 		1 - proceed ok
 * 		2 - biz exception
 * 		3 - idempotence
 *
 */
@Slf4j
@Component
@Aspect
public class MyAop {

	@Autowired private StorageProvider storageProvider;

	@Pointcut("@annotation(org.springframework.cloud.stream.annotation.StreamListener)")
	public void pointcut() {

	}

	@Around("pointcut()")
	public Object listenerAround(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();

		// TODO check args should only contains one Message type parameter.
		Message message = getMessage(args);
		ReceivedMessage receivedMessage = getReceivedMesage(message, pjp);
		log.debug("Framework debug consumer aspect received message: " + receivedMessage.toString());

		String channelName = getChannelName(pjp);

		/**
		 * currently, log message in elasticsearch.
		 * TODO update log into database.
		 */
		boolean dumpResult = dumpDatabase(receivedMessage, pjp);
		if(!dumpResult) {
			log.error("Framework debug - found duplicated record: " + receivedMessage.toString());
			sendResponseAck(message, channelName);
			// won't perform business logic.
			return null;
		}

		boolean hasBizException = false;
		try {
			Object result = pjp.proceed();
			return result;
		} catch(Exception ex) {
			hasBizException = true;
			log.error("Framework debug " + getCommonLog(message) + " Found business exception in aspect of MQ framework:" + ex.toString());
			throw ex;
		} finally {
			updateMessageStatusInDB(receivedMessage, channelName, hasBizException);
			sendResponseAck(message, channelName);
			log.debug("Framework debug " + getCommonLog(message) + " consumer aspect finish sending ack.");
		}
	}

	/**
	 * Return true if dump ok, return false if existing record with same unique key.
	 *
	 */
	private boolean dumpDatabase(ReceivedMessage receivedMessage, ProceedingJoinPoint pjp) throws Exception{
		String channelName = getChannelName(pjp);
		String tableName = MqDictionary.get(channelName).getTable();

		if(storageProvider.exist(receivedMessage, tableName)) {
			return false;
		}

		storageProvider.insert(receivedMessage, tableName);
		return true;
	}

	/**
	 *
	 */
	private void updateMessageStatusInDB(ReceivedMessage receivedMessage, String channelName, boolean hasException) {
		String tableName = MqDictionary.get(channelName).getTable();
		storageProvider.updateStatus(tableName, receivedMessage.getUniqueFieldValue(), !hasException ? 1 : 2);
	}

	private void sendResponseAck(Message<?> message, String channelName) throws Exception {
		MqTypeEnum mqTypeEnum = MqDictionary.get(channelName).getMqType();
		if(mqTypeEnum == MqTypeEnum.RABBIT) {
			sendAck4Rabbit(message);
		} else if(mqTypeEnum == MqTypeEnum.KAFKA) {
			sendAck4Kafka(message);
		} else {
			String error = "Framework error, Mq type " + mqTypeEnum + " is not supported for sending ack for "
					+ message.getHeaders().get("trace-id");
			log.error(error);
			throw new RuntimeException(error);
		}
	}

	private void sendAck4Rabbit(Message<?> message) throws Exception {
		Channel channel = null;
		Long deliveryTag = null;

		if(message instanceof ErrorMessage) {
			ErrorMessage errorMessage = (ErrorMessage) message;
			channel = (Channel) errorMessage.getOriginalMessage().getHeaders().get(AmqpHeaders.CHANNEL);
			deliveryTag = (Long) errorMessage.getOriginalMessage().getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		} else {
			channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
			deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		}

		channel.basicAck(deliveryTag, false);
		log.debug("Framework debug " + getCommonLog(message) + " Consumer sent rabbit ack for "
				+ ((message instanceof ErrorMessage) ? "error": "ok"));
	}

	private void sendAck4Kafka(Message<?> message) {
		Acknowledgment acknowledgment = null;

		if(message instanceof ErrorMessage) {
			ErrorMessage errorMessage = (ErrorMessage) message;
			acknowledgment = errorMessage.getOriginalMessage().getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		} else {
			acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		}

		acknowledgment.acknowledge();
		log.debug("Framework debug " + getCommonLog(message) + " Consumer sent kafka ack for "
				+ ((message instanceof ErrorMessage) ? "ok": "error"));
	}

	private Message getMessage(Object[] args) {
		Message message = null;
		for(Object arg : args) {
			if (arg instanceof Message) {
				message = (Message) arg;
				return message;
			}
		}
		return null;
	}

	private String getUniqueFieldValue(Message message) throws Exception {
		Field uniqueField = null;
		Object target = message.getPayload();
		for(Field field : target.getClass().getDeclaredFields()) {
			if(field.getAnnotation(MqKey.class) != null) {
				uniqueField = field;
				break;
			}
		}

		if(uniqueField == null) {
			return null;
		}

		// get field value via reflection.
		boolean isAccessible = uniqueField.isAccessible();
		uniqueField.setAccessible(true);
		String value = uniqueField.get(target).toString();
		uniqueField.setAccessible(isAccessible);
		return value;
	}

	private ReceivedMessage getReceivedMesage(Message message, ProceedingJoinPoint pjp) throws Exception {
		ReceivedMessage receivedMessage = new ReceivedMessage();
		receivedMessage.setCdate(new Date());
		receivedMessage.setEdate(new Date());
		receivedMessage.setData(message.getPayload().toString());
		receivedMessage.setHeader(message.getHeaders().toString());
		receivedMessage.setChannel(getChannelName(pjp));
		receivedMessage.setCreator("system");
		receivedMessage.setEditor("system");
		receivedMessage.setUniqueFieldValue(getUniqueFieldValue(message));
		receivedMessage.setTraceId(getTraceId(message));
		return receivedMessage;
	}

	private String getChannelName(ProceedingJoinPoint pjp) {
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		StreamListener sl = methodSignature.getMethod().getAnnotation(StreamListener.class);
		String channel = sl.value();
		return channel;
	}

	private String getTraceId(Message message) {
		return message.getHeaders().containsKey(MQ_TRACE_ID) ?
				message.getHeaders().get(MQ_TRACE_ID).toString() : "";
	}

	@SneakyThrows
	private String getCommonLog(Message message) {
		return MessageFormat.format(" trace-id is: {0} uniquekey is: {1} ",
				getTraceId(message),
				getUniqueFieldValue(message));
	}
}
