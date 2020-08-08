package com.ley.mod;

import com.ley.module.ClassA;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ClassA.class)
public class TestConfiguration {
	@Bean
	public ClassA classA() {
		ClassA a = new ClassA();
		a.setName("this is class a");
		System.out.println("calling classA......");
		return a;
	}
}
