package com.ley.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

/**
 * 1. invoke from spring.factories
 * or
 * 2. @Configuration on class, @Bean public EnvironmentPostProcessor mybeanmethod () {  return new BeanDefinitionRegistryPostProcessor() { ...} }
 *
 */
public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Properties properties = new Properties();
		properties.setProperty("gender", "male");

		PropertiesPropertySource source = new PropertiesPropertySource("ley", properties);

		environment.getPropertySources().addLast(source);
	}
}
