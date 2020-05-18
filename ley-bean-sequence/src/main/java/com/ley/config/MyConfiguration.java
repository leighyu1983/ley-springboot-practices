package com.ley.config;

import com.ley.entity.CustomBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
	@Bean(initMethod = "initMethod")
	public CustomBean customBean() {
		return new CustomBean();
	}
}
