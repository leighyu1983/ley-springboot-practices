package com.ley.configuration;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

	private String appName = "sparkExp";

	private String master = "local";

	@Bean
	@ConditionalOnMissingBean(SparkConf.class)
	public SparkConf sparkConf() throws Exception {
		SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
		return conf;
	}

	@Bean
	@ConditionalOnMissingBean
	public JavaSparkContext javaSparkContext() throws Exception {
		return new JavaSparkContext(sparkConf());
	}
}
