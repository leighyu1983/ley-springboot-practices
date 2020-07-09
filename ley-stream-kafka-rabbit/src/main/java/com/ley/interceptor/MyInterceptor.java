package com.ley.interceptor;


import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
@GlobalChannelInterceptor
public class MyInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> msg, MessageChannel mc) {
		return msg;
	}
}
