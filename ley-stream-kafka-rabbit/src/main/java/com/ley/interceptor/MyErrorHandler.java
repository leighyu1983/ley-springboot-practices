package com.ley.interceptor;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyErrorHandler {
	@Bean
	public ListenerContainerCustomizer haha() {
		return new ListenerContainerCustomizer() {
			@Override
			public void configure(Object container, String destinationName, String group) {
				ConcurrentMessageListenerContainer m = (ConcurrentMessageListenerContainer) container;
				m.setErrorHandler(new ErrorHandler() {
//					@Override
//					public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
//								MessageListenerContainer container) {
//						handle(thrownException, null);
//					}

					@Override
					public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {
						System.out.println(destinationName);
					}
				});
			}
		};
	}
}
