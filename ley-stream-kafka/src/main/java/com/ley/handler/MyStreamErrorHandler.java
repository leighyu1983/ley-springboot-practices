package com.ley.handler;

import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

@Configuration
public class MyStreamErrorHandler {
	@Bean
	public ListenerContainerCustomizer<AbstractMessageListenerContainer<?, ?>> customizer() {
		return (container, dest, group) -> container.setErrorHandler((ex, data) -> {
			System.out.println("-------------error handler----------");
//			System.out.println("------------------------------start---");
//			System.out.println(new String((byte[])( data.value())));
//			System.out.println(data);
//			System.out.println(ex);
//			System.out.println("------------------------------end-----");
		});
	}
}
