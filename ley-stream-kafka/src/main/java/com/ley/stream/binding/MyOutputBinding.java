package com.ley.stream.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * producer
 */
public interface MyOutputBinding {
	String OUTPUT_K3 = "output-K3";

	@Output(OUTPUT_K3)
	MessageChannel outPutK3();
}
