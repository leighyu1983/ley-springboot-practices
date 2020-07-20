package com.ley.interceptor;


import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;


@Component
//@GlobalChannelInterceptor
public class MyInterceptor implements ChannelInterceptor {

//	@Override
//	public Message<?> preSend(Message<?> msg, MessageChannel mc) {
//		// ErrorMessage also comes in
//		return msg;
//	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel mc, boolean bln, Exception exception) {

//		((ErrorMessage) message).getOriginalMessage()
//				.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class).acknowledge();

		try {
			if (getRequestType(message) == RequestType.PRODUCE) {
				System.out.println("In producer afterSendCompletion");
				processProduce(message, mc, exception);
			} else {
				System.out.println("In consumer afterSendCompletion");
				processConsume(message, mc, exception);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void processProduce(Message<?> message, MessageChannel mc, Exception exception) throws Exception {
		String msg = "";
		String payload = new String(((byte[])message.getPayload()));

		// rabbit
		if(mc instanceof DirectWithAttributesChannel) {
			String channelName = ((DirectWithAttributesChannel) mc).getFullChannelName();
			msg += channelName + " Payload:" + payload;
		}
		System.out.println("producer payload - " + msg);
	}

	//	DirectWithAttributesChannel a;
	//	AbstractSubscribableChannel b;
	//

	private void processConsume(Message<?> message, MessageChannel mc, Exception exception)  throws Exception {
		Message<?> msg;

		// dispatch different requests by different mq.
		if(exception != null) {
			handleException(message, mc, exception);
		} else if (message instanceof ErrorMessage) {
			// only for calling ack.
			handleErrorChannel(message);
		} else {
			handleSuccess(message, mc);
		}

		// send ack for consumer
		if(getRequestType(message) == RequestType.CONSUME) {
			sendResponseAck(message);
		}
	}

	private void handleSuccess(Message<?> message, MessageChannel mc) {
		RequestType requestType = getRequestType(message);
		if(requestType == RequestType.PRODUCE) {
			// log producer
			String payload = new String(((byte[])message.getPayload()));
			System.out.println(" Payload:" + payload);
		} else {
			// log consumer
			String payload = new String(((byte[])message.getPayload()));

			if(mc instanceof DirectWithAttributesChannel) {
				payload += " RabbitMq:" + ((DirectWithAttributesChannel) mc).getFullChannelName();
			}

			System.out.println(" Payload:" + payload);
		}
	}

	/**
	 * Currently, only happened on consumer side including deserialization issue and business exception.
	 *
	 * @param message
	 * @throws Exception
	 */
	private void handleErrorChannel(Message<?> message) throws Exception {
		// do nothing
	}

	/**
	 * Only handle consumer exception for now
	 *
	 * @param message
	 * @param mc
	 * @param exception
	 * @throws Exception
	 */
	private void handleException(Message<?> message, MessageChannel mc, Exception exception) throws Exception {
		RequestType requestType = getRequestType(message);
		String expt = "RequestType: " + requestType + " MessageChannel:" + mc.toString();

		String payload = new String(((byte[])message.getPayload()));

		// log exception
		//System.out.println(expt + " Payload:" + payload + " Exception:" + getExceptionStr(exception));
		System.out.println("--------------------------------------------------------------");
	}

	private void sendResponseAck(Message<?> message) throws Exception {
		// error also comes here
		if(getResponseMqType(message) == MqType.RABBIT) {
			Channel channel = null;
			Long deliveryTag = null;
			if(message instanceof ErrorMessage) {
				channel = (Channel) ((ErrorMessage) message).getOriginalMessage().getHeaders().get(AmqpHeaders.CHANNEL);
				deliveryTag = (Long) ((ErrorMessage) message).getOriginalMessage().getHeaders().get(AmqpHeaders.DELIVERY_TAG);
			} else {
				channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
				deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
			}

			channel.basicAck(deliveryTag, false);
		} else {
			Acknowledgment acknowledgment = null;
			Long deliveryTag = null;
			if(message instanceof ErrorMessage) {
				acknowledgment = (Acknowledgment) ((ErrorMessage) message).getOriginalMessage().getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT);
			} else {
				acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
			}

			acknowledgment.acknowledge();
			System.out.println("calling.......................why.....................");
		}
	}

	private String getExceptionStr(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	// need to test errorchannel
	private RequestType getRequestType(Message<?> message) {
		if(message.getHeaders().containsKey(AmqpHeaders.CHANNEL)
				|| message.getHeaders().containsKey(AmqpHeaders.RAW_MESSAGE)
				|| message.getHeaders().containsKey(KafkaHeaders.OFFSET)
				|| message.getHeaders().containsKey(KafkaHeaders.RAW_DATA)) {
			return RequestType.CONSUME;
		} else {
			return RequestType.PRODUCE;
		}
	}

	// need to test errorchannel
	private MqType getResponseMqType(Message<?> message) throws Exception {
		if(message.getHeaders().containsKey(AmqpHeaders.CHANNEL)
				// business exception
				|| message.getHeaders().containsKey(AmqpHeaders.RAW_MESSAGE)) {
			return MqType.RABBIT;
		} else if (message.getHeaders().containsKey(KafkaHeaders.OFFSET)
				// business exception
				|| message.getHeaders().containsKey(KafkaHeaders.RAW_DATA)) {
			return MqType.KAFKA;
		} else {
			throw new Exception("mq type is not supported");
		}
	}

	private enum RequestType {
		PRODUCE, CONSUME
	}

	private enum MqType {
		RABBIT, KAFKA
	}
}
