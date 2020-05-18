package com.ley.controller;

import com.ley.entity.LeyStringValueCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	// cannot get
	@Value("${gender}")
	private String gender;

	//@Autowired private Environment env;

	@GetMapping("/post-processor/get")
	public String getPostProcessor() {
		//return "gender is .... " + env.getProperty("gender");
		return "gender is .... " + gender;
	}

	@GetMapping("/post-processor/update/{val}")
	public void updatePostProcessor(@PathVariable("val") String newValue) {
		LeyStringValueCollection.getManager().onChange("gender", newValue);
	}
}
