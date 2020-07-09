package com.ley.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MyRabbitConsumerR1Binding {
	String consumerRA = "consumerRA";
	String consumerRA2 = "consumerRA2";
	String direct_R_consumer_A = "direct_R_consumer_A";
	String fanout_R_consumer_cA1 = "fanout_R_consumer_cA1";
	String fanout_R_consumer_cA2 = "fanout_R_consumer_cA2";


	@Input(consumerRA)
	SubscribableChannel consumerRA();

	@Input(consumerRA2)
	SubscribableChannel consumerRA2();

	@Input(direct_R_consumer_A)
	SubscribableChannel direct_R_consumer_A();

	@Input(fanout_R_consumer_cA1)
	SubscribableChannel fanout_R_consumer_cA1();

	@Input(fanout_R_consumer_cA2)
	SubscribableChannel fanout_R_consumer_cA2();
}
