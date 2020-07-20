package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * consumer
 */
public interface MyInputBinding {
	String INPUT_K3 = "input-K3";

	@Input(INPUT_K3)
	SubscribableChannel inputK3();
}
