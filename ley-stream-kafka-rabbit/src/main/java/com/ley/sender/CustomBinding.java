package com.ley.sender;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *  Ignore default settings.
 *
 *  // extends Source, Sink {
 *
 */
public interface CustomBinding {

	/**
	 * Four testing servers, each has a input/output
	 */
	String INPUT_R1 = "input-R1";
	String OUTPUT_R1 = "output-R1";

	String INPUT_R2 = "input-R2";
	String OUTPUT_R2 = "output-R2";

	String INPUT_K1 = "input-K1";
	String OUTPUT_K1 = "output-K1";

	String INPUT_K2 = "input-K2";
	String OUTPUT_K2 = "output-K2";


	@Input(INPUT_R1)
	SubscribableChannel inputR1();

	@Output(OUTPUT_R1)
	MessageChannel outputR1();

	@Input(INPUT_R2)
	SubscribableChannel inputR2();

	@Output(OUTPUT_R2)
	MessageChannel outputR2();

	@Input(INPUT_K1)
	SubscribableChannel inputK1();

	@Output(OUTPUT_K1)
	MessageChannel outputK1();

	@Input(INPUT_K2)
	SubscribableChannel inputK2();

	@Output(OUTPUT_K2)
	MessageChannel outputK2();
}
