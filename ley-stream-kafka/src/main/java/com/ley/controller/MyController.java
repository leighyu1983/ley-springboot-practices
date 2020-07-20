package com.ley.controller;

import com.ley.stream.binding.MyOutputBinding;
import com.ley.stream.entity.PersonObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@EnableBinding(MyOutputBinding.class)
public class MyController {

	@Autowired private MyOutputBinding myOutputBinding;

	@GetMapping("kaf-ex/{a1}")
	public void send(@PathVariable("a1") String a) {
		Message<PersonObj> message = MessageBuilder.withPayload(new PersonObj("tom"+a, 12, new Date())).build();
		System.out.println("...send....out....");
		myOutputBinding.outPutK3().send(message);
	}
}
