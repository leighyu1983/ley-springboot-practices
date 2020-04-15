package com.ley.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {
	@GetMapping("loop")
	public String ping() {
		for (int i = 0; i < 3; i++) {
			System.out.println("i: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}
		return "1";
	}
}
