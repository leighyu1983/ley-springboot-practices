package com.ley.interfaces;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

public class MyFeignClientUserConfig {

	@Value("${auth.username:#{null}}")
	private String username;

	@Value("${auth.password:#{null}}")
	private String password;

	@Bean
	@ConditionalOnProperty({"auth.username", "auth.password"})
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor(username, password, feign.Util.UTF_8);
	}
}

