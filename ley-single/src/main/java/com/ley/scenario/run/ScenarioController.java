package com.ley.scenario.run;

import com.ley.scenario.mystring.MyStringSample;
import com.ley.scenario.serialization.SerializeDemo;
import com.ley.scenario.serialization.SerializeObjWithJackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scenario")
public class ScenarioController {

	@GetMapping("/serializeWithoutSerialization")
	public String serializeWithoutSerialization() {
		SerializeDemo.serializeWithoutSerialization();
		SerializeDemo.deserializeWithoutSerialization();
		return "done....";
	}

	@GetMapping("/serializeWithSerialization")
	public String serializeWithSerialization() {
		SerializeDemo.serializeWithSerialization();
		SerializeDemo.deserializeWithSerialization();
		return "done....";
	}

	@PostMapping("/serializeConnectionLine")
	public String serializeConnectingLine(@RequestBody SerializeObjWithJackson json) {
		System.out.println("....." + json);
		return "serializeConnectionLine  done....";
	}

	@GetMapping("/string/one")
	public String stringOne() {
		MyStringSample.testOne();
		return "done....";
	}

	@Autowired private MyStringSample myStringSample;
	@GetMapping("/string/two")
	public String stringTwo() {
		//myStringSample.testTwo();
		return "done....";
	}
}
