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

	/**
	 * TODO add  configurationProperties and properties files
	 */
	@Value("${gender}")
	private String gender;

	@Autowired private MyPropertyConfig myPropertyConfig;

	//@Autowired private Environment env;

	@GetMapping("/post-processor/get")
	public String getPostProcessor() {
		String you = myPropertyConfig.getYou();
		//return "gender is .... " + env.getProperty("gender");
		return "gender is -> " + gender + ".......you is -> " + you;
	}

	@GetMapping("/post-processor/update/{val1}/{val2}")
	public void updatePostProcessor(@PathVariable("val1") String newGender, @PathVariable("val2") String newYou) {
		LeyStringValueCollection.getManager().onChange("gender", newGender);
		LeyStringValueCollection.getManager().onChange("you", newYou);
	}
}
