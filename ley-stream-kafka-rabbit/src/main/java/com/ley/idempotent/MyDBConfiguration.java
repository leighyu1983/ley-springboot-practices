package com.ley.idempotent;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MyDBConfiguration {

	/**
	 * custom side
	 * @param dataSource
	 * @return
	 */
	@Bean
	public IdempotentProvider IdempotentProvider(DataSource dataSource) {
		return new IdempotentProvider(dataSource);
	}
}
