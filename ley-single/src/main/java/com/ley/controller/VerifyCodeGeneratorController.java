package com.ley.controller;

import com.ley.event.myuser.service.UserPublishEventService;
import com.ley.ioc.MyBeanA;
import com.ley.pojo.NormalBean;
import com.ley.tools.VerifyCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@Slf4j
public class VerifyCodeGeneratorController {

	@Autowired UserPublishEventService userPublishEventService;
	@Autowired(required = false) MyBeanA myBeanA;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

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

	@GetMapping("/test/compare-comparator")
	public String testComparator() {
		List<NormalBean> nbs = Arrays.asList(
			new NormalBean("tom", 10),
			new NormalBean("tom", 30),
			new NormalBean("tom", 20)
		);
		Collections.sort(nbs, new Comparator<NormalBean>() {
			@Override
			public int compare(NormalBean o1, NormalBean o2) {
				//return o1.getAge() - o2.getAge();
				return o2.getAge() - o1.getAge();
			}
		});
		
		log.info(nbs.toString());
		return "done";
	}

	@GetMapping("/test/compare-comparable")
	public String testComparable() {
		return "done";
	}
}
