package com.ley.listener;

import com.ley.stream.binding.MyInputBinding;
import com.ley.stream.entity.PersonObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@Slf4j
@EnableBinding(MyInputBinding.class)
public class MyKafkaListener {

	@StreamListener(MyInputBinding.INPUT_K3)
	public void recieveK1(Message<PersonObj> message) {
		//System.out.println("ok business listener...............");
		System.out.println("before throwing exception...............");
		throw new RuntimeException("..........this is runtime exception..............");
	}
}
