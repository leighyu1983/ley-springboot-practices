package com.ley.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
public class GlobalExceptionHandler {
	/**
	 * Cannot handle the situation that "MessageDeliveryException: failed to send Message to channel 'errorChannel'"
	 * E.g. MessageConversionException
	 *
	 * @param message
	 * @throws IOException
	 */
	//@StreamListener("errorChannel")
	public void error(ErrorMessage message) throws IOException {
		Channel channel = (com.rabbitmq.client.Channel)message.getOriginalMessage().getHeaders().get(AmqpHeaders.CHANNEL);
		if(channel != null) {
			Long deliveryTag = (Long) message.getOriginalMessage().getHeaders().get(AmqpHeaders.DELIVERY_TAG);
			channel.basicAck(deliveryTag, false);
		}

		System.out.println("Handling ERROR: " + message);
	}
}
