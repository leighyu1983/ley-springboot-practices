package com.ley.stream.interceptor;


import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.integration.amqp.support.ReturnedAmqpMessageException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@GlobalChannelInterceptor
public class MyMessageInterceptor4Send implements ChannelInterceptor {

	@Value("${spring.application.name:application}")
	private String appliactionName;

	private final Map<String, BlockingQueue<ReturnedAmqpMessageException>> returned = new ConcurrentHashMap<>();
	private final Map<String, BlockingQueue<Boolean>> confirms = new ConcurrentHashMap<>();

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		String id = message.getHeaders().get(AmqpHeaders.CORRELATION_ID, String.class);
		this.returned.put(id, new LinkedBlockingQueue<>());
		this.confirms.put(id, new LinkedBlockingQueue<>());
		return message;
	}


	@Override
	public void afterSendCompletion(
			Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {

		log.debug("............current sender aop thread" + Thread.currentThread().getId());
		//message.getPayload();
		String id = message.getHeaders().get(AmqpHeaders.CORRELATION_ID, String.class);

		try {
			Boolean confirm = this.confirms.get(id).poll(10, TimeUnit.SECONDS);
			this.confirms.remove(id);
			if (confirm == null) {
				log.warn("Remove " + id);
				this.returned.remove(id);
				throw new RuntimeException("No confirm received");
			}
			log.info(confirm.toString());
			BlockingQueue<ReturnedAmqpMessageException> returnQ = this.returned.get(id);
			// We have to wait a short time here due to https://github.com/spring-projects/spring-amqp/issues/1055
			// Arrival of return before confirm should be guaranteed by the framework.
			ReturnedAmqpMessageException returnedEx = returnQ.poll(50, TimeUnit.MILLISECONDS);
			this.returned.remove(id);
			if (returnedEx != null) {
				log.error(returnedEx.toString());
				throw new RuntimeException("Message returned");
			}

			/**
			 * CHANNEL							EXCEPTION	MESSAGE				CHANNEL				SENT		COMMENTS
			 * DirectWithAttributesChannel		no			GenericMessage		output-x			true
			 * DirectWithAttributesChannel		no			GenericMessage		input-x				true
			 * DirectWithAttributesChannel		yes			GenericMessage		input-x				false
			 * PublishSubscribeChannel			no			ErrorMessage		bean(errorChannel)	true
			 */

			MqObj mqObj = getMqObj(message, channel);

			log.debug("tracing mode...."
					+ (sent ? "sent" : "receive")
					+ "--->parsedObj: " + mqObj.toString()
					+ "--->message-type: " + message.getClass().getName()
					+ "--->payload: " + getPayload(message)
					+ "--->has exception:" + (ex == null ? "no" : "yes"));

//			if (mqObj.requestType == RequestType.OUTPUT) {
//				log.debug("tracing mode...."
//						+ (sent ? "sent" : "receive")
//						+ "--->parsedObj: " + mqObj.toString()
//						+ "--->message-type: " + message.getClass().getName()
//						+ "--->payload: " + getPayload(message)
//						+ "--->has exception:" + (ex == null ? "no" : "yes"));
//			}

//		if(mqObj.resultType == ResultType.OK) {
//			if(mqObj.mqType == MqType.KAFKA) {
//				sendSuccessKafkaAck(message);
//			}
//
//			if(mqObj.mqType == MqType.RABBIT) {
//				sendSuccessRabbitAck(message);
//			}
//		}
//
//		if(mqObj.resultType == ResultType.ERROR) {
//			if(mqObj.mqType == MqType.KAFKA) {
//				sendSuccessKafkaAck(message);
//			}
//
//			if(mqObj.mqType == MqType.RABBIT) {
//				sendSuccessRabbitAck(message);
//			}
//		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

//	private void sendSuccessRabbitAck(Message<?> message) {
//		Channel rabbitChannel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
//		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//		try {
//			rabbitChannel.basicAck(deliveryTag, false);
//		} catch (Exception ex1) {
//			log.error(ex1.toString());
//		}
//	}
//
//	private void sendSuccessKafkaAck(Message<?> message) {
//		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
//		acknowledgment.acknowledge();
//	}


	private String getPayload(Message<?> message) {
		byte[] bytes = new byte[0];

		if (message instanceof GenericMessage) {
			bytes = (byte[]) message.getPayload();
		}

		if (message instanceof ErrorMessage) {
			ErrorMessage errorMessage = (ErrorMessage) message;
			bytes = (byte[]) errorMessage.getOriginalMessage().getPayload();
		}

		return new String(bytes);
	}

	private MqObj getMqObj(Message<?> message, MessageChannel channel) {
		if (channel instanceof DirectWithAttributesChannel) {
			DirectWithAttributesChannel myChannel = (DirectWithAttributesChannel) channel;
			String channelName = myChannel.getFullChannelName();
			return new MqObj(
					getMqType(channelName),
					getRequestType(channelName),
					ResultType.OK,
					channelName,
					channel.getClass().getName()
			);
		}

		//
		if (channel instanceof PublishSubscribeChannel && message instanceof ErrorMessage) {
			log.debug("");
			return new MqObj(
					MqType.KAFKA, // wrong
					RequestType.INPUT, // wrong
					ResultType.ERROR,
					"errorChannel", // errorChannel
					channel.getClass().getName()
			);
		}

		return null;
	}

	private MqType getMqType(String channelName) {
		return channelName.replace(appliactionName + ".", "").toLowerCase()
				.split("-")[1].equals("k") ? MqType.KAFKA : MqType.RABBIT;
	}

	private RequestType getRequestType(String channelName) {
		return channelName.replace(appliactionName + ".", "").toLowerCase()
				.split("-")[0].equals("input") ? RequestType.INPUT : RequestType.OUTPUT;
	}

	@ServiceActivator(inputChannel = "acks")
	public void handleAcks(Message<?> msg) {
		this.confirms.get(msg.getHeaders().get(AmqpHeaders.CORRELATION_ID, String.class))
				.add(msg.getHeaders().get(AmqpHeaders.PUBLISH_CONFIRM, Boolean.class));
	}

	@ServiceActivator(inputChannel = "errorChannel")
	public void handleErrors(Message<?> msg) {
		log.info(msg.toString());
		if (msg.getPayload() instanceof ReturnedAmqpMessageException) {
			ReturnedAmqpMessageException exception = (ReturnedAmqpMessageException) msg.getPayload();
			log.info(this.returned.keySet().toString());
			this.returned.get(exception.getAmqpMessage().getMessageProperties().getCorrelationId())
					.add(exception);
		}
	}

	@ToString
	@AllArgsConstructor
	private class MqObj {
		MqType mqType;
		RequestType requestType;
		ResultType resultType;
		String channelName;
		String channelType;
	}

	public enum MqType {
		RABBIT, KAFKA
	}

	public enum RequestType {
		INPUT, OUTPUT
	}

	public enum ResultType {
		OK, ERROR
	}
}
