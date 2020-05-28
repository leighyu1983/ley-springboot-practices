package com.ley.scenario.run;

import com.ley.scenario.serialization.SerializeDemo;
import com.ley.scenario.serialization.SerializeObjWithJackson;
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

}
