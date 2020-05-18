package com.ley.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property")
public class ValueController {
	@Value("${ley-name:1100}")
	private String leyName;

	@Autowired ConfigurableEnvironment environment;

	@GetMapping("")
	public String get() {
		return "get ley-name string is " + leyName;
	}

	@GetMapping("/update")
	public String update() {
		return "call update";
	}

	@GetMapping("/property/loop/print")
	public void loopPrint() throws Exception {
		for (; ; ) {
			Thread.sleep(1000);
			System.out.println(leyName);
		}
	}

	@GetMapping("/property/loop/update")
	public void loopUpdate(@RequestParam("name") String name) throws Exception {
		PropertySource ps = environment.getPropertySources().get("ley-name");
		ps.
	}
}
