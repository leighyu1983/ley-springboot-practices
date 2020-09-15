package com.ley.scenario.httppool;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http")
public class MyHttpConfigurationProperty {
	/**
	 * Max connections in connection pool.
	 */
	private int maxTotal = 20;

	/**
	 * Max connections per route.
	 */
	private int maxPerRoute = 3;

	private int connectionTimeoutMs = 30 * 1000;
	private int readTimeoutMs = 30 * 1000;
	private int connectionRequestTimeoutMs = 5 * 1000;
}
