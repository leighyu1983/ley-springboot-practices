package com.ley.controller;

import com.ley.entity.School;
import com.ley.sender.MySender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
public class TestController {

	@Autowired private MySender mySender;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("/rabbit-object-single")
	public void rabbitSendObj() {
		School school = new School();
		school.setCreatedOn(new Date());
		school.setName("name-tom");
		school.setType("university");
		mySender.sendR1Msg(school);
	}

	@GetMapping("/rabbit-single-direct")
	public void rabbitSendDirect() {
		School school = new School();
		school.setCreatedOn(new Date());
		school.setName("name-tom");
		school.setType("university");
		mySender.sendR3Msg(school);
	}

	@GetMapping("/rabbit-single-fanout")
	public void rabbitSendFanout() {
		School school = new School();
		school.setCreatedOn(new Date());
		school.setName("name-tom");
		school.setType("university");
		mySender.sendR4Msg(school);
	}

	@GetMapping("/rabbit-object-list")
	public void rabbitSendObjList() {
		List<School> schools = new ArrayList<>();
		School school1 = new School();
		school1.setCreatedOn(new Date());
		school1.setName("name-tom-1");
		school1.setType("university-1");
		schools.add(school1);

		School school2 = new School();
		school2.setCreatedOn(new Date());
		school2.setName("name-tom-2");
		school2.setType("university-2");
		schools.add(school2);

		mySender.sendR2Msg(schools);
	}

	@GetMapping("/kafka-object")
	public void kafkaSendObj() {
		School school = new School();
		school.setCreatedOn(new Date());
		school.setName("name-tom");
		school.setType("university");
		mySender.sendK1Msg(school);
	}
}
