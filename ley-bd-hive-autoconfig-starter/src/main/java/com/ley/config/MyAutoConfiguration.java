package com.ley.config;

import com.ley.pojo.MyUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@ConditionalOnProperty(name = "hc.hive", havingValue = "")
@EnableConfigurationProperties(MyConfig.class)
public class MyAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public MyUser myUser() {
		return new MyUser();
	}
}
