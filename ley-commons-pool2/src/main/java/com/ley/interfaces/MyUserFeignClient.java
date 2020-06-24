package com.ley.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "MyUserFeignClient", url = "${auth.url}/agent", configuration = MyFeignClientUserConfig.class)
@RequestMapping
public interface MyUserFeignClient {

	@GetMapping("/me")
	String me(@RequestParam("name") String name);

}
