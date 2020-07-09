package com.ley.idempotent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyDBConfiguration {
	@Bean
	public IdempotentProvider IdempotentProvider(DataSource dataSource) {
		return new IdempotentProvider(dataSource);
	}
}
