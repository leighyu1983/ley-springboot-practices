package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * producer
 */
public interface MyOutputBinding {
	String OUTPUT_R3 = "output-r3";

	@Output(OUTPUT_R3)
	MessageChannel outPutR3();
}
