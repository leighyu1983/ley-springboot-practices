package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * producer
 */
public interface MyOutputBinding {
	String RABBIT_OUTPUT_CHANNEL_PREFIX = "output-r-";
	String KAFKA_OUTPUT_CHANNEL_PREFIX = "output-k-";

	// rabbitmq direct exchange, queue named direct-q1
	String OUTPUT_R_DIRECT_Q1 = RABBIT_OUTPUT_CHANNEL_PREFIX + "direct-q1";

	@Output(OUTPUT_R_DIRECT_Q1)
	MessageChannel outPutRDirectQ1();
}
