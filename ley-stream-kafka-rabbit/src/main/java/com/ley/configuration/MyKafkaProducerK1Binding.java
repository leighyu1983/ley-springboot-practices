package com.ley.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


public interface MyKafkaProducerK1Binding {
	String PRODUCER_K1 = "producer-K1";

	@Output(PRODUCER_K1)
	MessageChannel outputK1();
}
