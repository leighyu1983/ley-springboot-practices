package com.ley.controller;

import com.lei.component.HelloComponent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

	@Resource HelloComponent helloComponent;
	@RequestMapping("/hi")
	public String hello() {
		return helloComponent.say("nice");
	}
}
