package com.ley.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "a")
public class MyConfig {
	private String url;
}
