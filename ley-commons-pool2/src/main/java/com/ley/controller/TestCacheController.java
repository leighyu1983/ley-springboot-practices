package com.ley.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCacheController {
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
	@GetMapping("/test-s")
	public int getThreads() {
		return Thread.getAllStackTraces().size();
	}

	/**
	 * info clients
	 * client list
	 *
	 * @return
	 */
	@GetMapping("/test-mo")
	public int testMemoryOccupy() {

		return 1;
	}
}
