package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * consumer
 */
public interface MyInputBinding {
	String INPUT_R3_1 = "input-r3-1";
	String INPUT_R3_2 = "input-r3-2";

	@Input(INPUT_R3_1)
	SubscribableChannel inputR3_1();
	//@Input(INPUT_R3_2)
	SubscribableChannel inputR3_2();
}
