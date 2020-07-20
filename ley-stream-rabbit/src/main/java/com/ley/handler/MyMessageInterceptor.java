package com.ley.handler;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalChannelInterceptor
public class MyMessageInterceptor implements ChannelInterceptor {

	@Override
	public void afterSendCompletion(
			Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {

		/**
		 * CHANNEL							EXCEPTION	MESSAGE				CHANNEL				SENT		COMMENTS
		 * DirectWithAttributesChannel		no			GenericMessage		output-x			true
		 * DirectWithAttributesChannel		no			GenericMessage		input-x				true
		 * DirectWithAttributesChannel		yes			GenericMessage		input-x				false
		 * PublishSubscribeChannel			no			ErrorMessage		bean(errorChannel)	true
		 */

		System.out.println("------------------------------------------");
		if(channel instanceof DirectWithAttributesChannel) {

			DirectWithAttributesChannel myChannel = (DirectWithAttributesChannel) channel;
			System.out.println("attribute--->" + myChannel.getFullChannelName());
			// for consumer ack.
			if(myChannel.getFullChannelName().toLowerCase().contains("input")) {
				Channel rabbitChannel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
				Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
				try {
					rabbitChannel.basicAck(deliveryTag, false);
				} catch (Exception ex1) {
					ex1.printStackTrace();
				}
			}
		}

		if(channel instanceof PublishSubscribeChannel) {
			PublishSubscribeChannel myChannel = (PublishSubscribeChannel) channel;
			System.out.println("error message attribute--->" + myChannel.getFullChannelName());
		}

		message.getHeaders().forEach((x, y) -> System.out.println("header-key:" + x + " header-value:" + y) );
		System.out.println("tracing mode...."
				+ (sent ? "sent" : "receive")
				+ "--->channel: " + channel.getClass().getName()
				+ "--->message: " + message.getClass().getName()
				+ "--->has exception:" + (ex == null ? "no" : "yes"));

	}

	private void log(boolean sent, Class channelClazz, String channelName, Message<?> message, Exception ex) {
		System.out.println("tracing mode...."
				+ (sent ? "out" : "in")
				+ "--->channel: " + channelClazz.getName() + "--->" + channelName
				+ "--->message: " + message.getClass().getName()
				+ "--->has exception:" + (ex == null?"no":"yes"));
	}
}
