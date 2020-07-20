package com.ley.listener;

import com.ley.stream.binding.MyInputBinding;
import com.ley.stream.entity.PersonObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(MyInputBinding.class)
public class MyRabbitListener {

	/**
	 * rabbit direct with multiple queues
	 * ack
	 * exception
	 *
	 *
	 * @param message
	 */
	@StreamListener(MyInputBinding.INPUT_R3_1)
	public void recieveR3_1(Message<PersonObj> message) {
		//System.out.println("ok business listener...r3-1............" + message.getPayload().toString());
		System.out.println("before throwing exception...............");
		throw new RuntimeException("..........this is runtime exception..............");
	}

	//@StreamListener(MyInputBinding.INPUT_R3_2)
	public void recieveR3_2(Message<PersonObj> message) {
		System.out.println("ok business listener...r3-2............" + message.getPayload().toString());
		//System.out.println("before throwing exception...............");
		//throw new RuntimeException("..........this is runtime exception..............");
	}
}
