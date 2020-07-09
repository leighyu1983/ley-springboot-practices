package com.ley.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MyKafkaConsumerK1Binding {
	String CONSUMER_K1 = "consumer-K1";

	@Input(CONSUMER_K1)
	SubscribableChannel inputK1();
}
