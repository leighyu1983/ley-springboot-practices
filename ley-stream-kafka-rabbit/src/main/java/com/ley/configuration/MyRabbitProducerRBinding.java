package com.ley.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MyRabbitProducerRBinding {
	String PRODUCER_RA = "producerRA";
	String PRODUCER_RA2 = "producerRA2";
	String direct_R_producer_A = "direct_R_producer_A";
	String fanout_R_producer_A = "fanout_R_producer_A";

	@Output(PRODUCER_RA)
	MessageChannel outputR1();

	@Output(PRODUCER_RA2)
	MessageChannel outputR2();

	@Output(direct_R_producer_A)
	MessageChannel outputR3();

	@Output(fanout_R_producer_A)
	MessageChannel outputR4();
}
