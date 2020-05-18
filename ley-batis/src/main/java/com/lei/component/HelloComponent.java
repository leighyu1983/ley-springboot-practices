package com.lei.component;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

public class HelloComponent {
	AnnotatedBeanDefinitionReader a;
	ConfigurationClassPostProcessor b;
	public String say(String param) {
		return "saying... " + param;
	}
}
