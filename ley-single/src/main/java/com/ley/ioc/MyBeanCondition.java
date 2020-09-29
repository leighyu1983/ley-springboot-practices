package com.ley.ioc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeanCondition {
	@Bean
	// 某个class位于类路径上，才会实例化一个Bean, 如Pom中引用的自定义jar.
	@ConditionalOnClass(name = "com.yel.YelSample")
	public MyBeanA myBeanA() {
		AnnotationConfigApplicationContext m = new AnnotationConfigApplicationContext();
		m.getBean("");
		return new MyBeanA("abc");
	}
}


