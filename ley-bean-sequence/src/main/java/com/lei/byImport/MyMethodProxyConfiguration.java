package com.lei.byImport;

import com.lei.entity.Interests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMethodProxyConfiguration {

	// bean name is interests
	@Bean
	public Interests interests() {
		System.out.println("第12步：通过ImportSelector注入其他package中的Bean, 该方法为Spring3.0提供@Import方式, 也可以用Spring.factories来实现。");
		return new Interests();
	}
}
