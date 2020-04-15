package com.ley.controller;

import com.ley.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired private CacheService cacheService;

	@GetMapping("/test-a")
	public String testA() {
		cacheService.set("a", "1");
		return cacheService.get("a");
	}

	@GetMapping("/test-s")
	public int getThreads() {
		return Thread.getAllStackTraces().size();
	}
}
