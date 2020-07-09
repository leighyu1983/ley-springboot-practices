package com.ley.bean;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


@Configuration
@EnableConfigurationProperties(KafkaBinderConfigurationProperties.class)
public class TestBean implements BeanPostProcessor {

	private KafkaBinderConfigurationProperties kafkaBinderConfigurationPropertie;

	public TestBean(
			@Qualifier("spring.cloud.stream.kafka.binder-org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties")
			KafkaBinderConfigurationProperties kafkaBinderConfigurationPropertie) {
		this.kafkaBinderConfigurationPropertie = kafkaBinderConfigurationPropertie;

//		this.kafkaBinderConfigurationPropertie.getConsumerProperties().put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "10086");
//		this.kafkaBinderConfigurationPropertie.getConsumerProperties().put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "68001");
	}
}
