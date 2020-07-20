package com.ley.handler;

import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
@GlobalChannelInterceptor
public class MyMessageInterceptor implements ChannelInterceptor {

	@Override
	public void afterSendCompletion(
			Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {

		DirectWithAttributesChannel a =
				channel instanceof DirectWithAttributesChannel ? (DirectWithAttributesChannel)channel : null;

		PublishSubscribeChannel b = (PublishSubscribeChannel)channel;

		/**
		 * CHANNEL							EXCEPTION	MESSAGE				CHANNEL				SENT		COMMENTS
		 * DirectWithAttributesChannel		no			GenericMessage		output-x			true		producer ok
		 * DirectWithAttributesChannel		no			GenericMessage		input-x				true		listener ok
		 * DirectWithAttributesChannel		yes			GenericMessage		input-x				false 		listener business code throws exception
		 * PublishSubscribeChannel			no			ErrorMessage		bean(errorChannel)	true		framework handling exception
		 */

		System.out.println("tracing mode...."
				+ (sent ? "sent":"receive")
				+ "--->channel: " + (a == null ? channel.getClass().getName() : a.getClass().getName() + "--->" + a.getFullChannelName())
				+ "--->message: " + message.getClass().getName()
				+ "--->has exception:" + (ex == null?"no":"yes"));

		// input or output success scenario
		if(channel instanceof  DirectWithAttributesChannel && sent) {
			DirectWithAttributesChannel myChannel = (DirectWithAttributesChannel)channel;
			// log
			log(sent, myChannel.getClass(), myChannel.getFullChannelName(), message, ex);

			if(myChannel.getFullChannelName().toLowerCase().contains("input")) {
				// input need ack
				message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class).acknowledge();
			} else {
				// output no need ack
			}
		}
		// listener throws exception, log message, no ack
		else if (channel instanceof  DirectWithAttributesChannel && !sent) {
			DirectWithAttributesChannel myChannel = (DirectWithAttributesChannel)channel;
			log(sent, myChannel.getClass(), myChannel.getFullChannelName(), message, ex);
		}
		// business listener throws exception, need ack.
		else if (message instanceof ErrorMessage) {
			PublishSubscribeChannel myChannel = (PublishSubscribeChannel)channel;
			log(sent, myChannel.getClass(), myChannel.getFullChannelName(), message, ex);

			((ErrorMessage) message).getOriginalMessage()
				.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class).acknowledge();
		} else {
			log(sent, channel.getClass(), "not known", message, ex);
		}
	}

	private void log(boolean sent, Class channelClazz, String channelName, Message<?> message, Exception ex) {
		System.out.println("tracing mode...."
				+ (sent ? "out" : "in")
				+ "--->channel: " + channelClazz.getName() + "--->" + channelName
				+ "--->message: " + message.getClass().getName()
				+ "--->has exception:" + (ex == null?"no":"yes"));
	}
}
