package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * consumer
 */
public interface MyInputBinding {
	String RABBIT_INPUT_CHANNEL_PREFIX = "input-r-";
	String KAFKA_INPUT_CHANNEL_PREFIX = "input-k-";

	// rabbitmq direct exchange, queue named direct-q1
	String INPUT_R_DIRECT_Q1 = RABBIT_INPUT_CHANNEL_PREFIX + "direct-q1";
	// rabbitmq fanout exchange, queue named fanout-q1
	String INPUT_R_FANOUT_Q1 = RABBIT_INPUT_CHANNEL_PREFIX + "fanout-q1";
	// rabbitmq fanout exchange, queue named fanout-q2
	String INPUT_R_FANOUT_Q2 = RABBIT_INPUT_CHANNEL_PREFIX + "fanout-q2";
	// rabbitmq topic exchange, queue named topic-q1
	String INPUT_R_TOPIC_Q1 = RABBIT_INPUT_CHANNEL_PREFIX + "topic-q1";
	// rabbitmq exception, topic exchange, queue named exception-q2
	String INPUT_R_TOPIC_EXCEPTION_Q2 = RABBIT_INPUT_CHANNEL_PREFIX + "topic-exception-q2";
	// rabbitmq direct exchange, broadcast queue named broadcast-q3
	String INPUT_R_TOPIC_BROAD_CASE_Q3 = RABBIT_INPUT_CHANNEL_PREFIX + "topic-broadcast-q3";
	// rabbitmq direct exchange, broadcast queue named broadcast-q4
	String INPUT_R_TOPIC_BROAD_CASE_Q4 = RABBIT_INPUT_CHANNEL_PREFIX + "topic-broadcast-q4";

	// kakfa queue named q10
	String INPUT_K_Q10 = KAFKA_INPUT_CHANNEL_PREFIX + "q10";
	// kakfa exception queue named q11
	String INPUT_K_EXCEPTION_Q11 = KAFKA_INPUT_CHANNEL_PREFIX + "exception-q11";


	@Input(INPUT_R_DIRECT_Q1)
	SubscribableChannel inputRDirectQ1();

}
