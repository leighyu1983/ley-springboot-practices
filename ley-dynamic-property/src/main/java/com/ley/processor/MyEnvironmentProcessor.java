package com.ley.processor;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class MyEnvironmentProcessor implements BeanPostProcessor, EnvironmentAware {
	@Override
	public void setEnvironment(Environment environment) {

	}
}
