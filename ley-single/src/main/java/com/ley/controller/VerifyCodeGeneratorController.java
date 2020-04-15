package com.ley.controller;

import com.ley.tools.VerifyCodeGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyCodeGeneratorController {

	@GetMapping("/test/code")
	public void TestCodeGenerator() {
		VerifyCodeGenerator.getVerifyCodeMultipleThreading(4);
	}
}
