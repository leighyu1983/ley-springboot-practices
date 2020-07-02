package com.ley.controller;

import com.ley.entity.School;
import com.ley.sender.MySender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RestController
public class TestController {

	@Autowired private MySender mySender;

	@GetMapping("/kafka-string")
	public void kafkaSendStr() {
		mySender.sendMsg("this is abc");
	}

	@GetMapping("/kafka-object")
	public void kafkaSendObj() {
		School school = new School();
		school.setCreatedOn(new Date());
		school.setName("name-tom");
		school.setType("university");
		mySender.sendMsg(school);
	}
}
