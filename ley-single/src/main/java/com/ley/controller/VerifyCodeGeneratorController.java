package com.ley.controller;

import com.ley.event.myuser.service.UserPublishEventService;
import com.ley.ioc.MyBeanA;
import com.ley.tools.VerifyCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyCodeGeneratorController {

	@Autowired UserPublishEventService userPublishEventService;
	@Autowired(required = false) MyBeanA myBeanA;

	@GetMapping("/test/code")
	public void TestCodeGenerator() {
		VerifyCodeGenerator.getVerifyCodeMultipleThreading(4);
	}

	@GetMapping("/test/event")
	public void testApplication() { userPublishEventService.register("you are go good......"); }

	@GetMapping("/test/bean")
	public String testBean() {
		if (myBeanA == null) {
			return "...null...";
		} else {
			return myBeanA.getName();
		}
	}
}
