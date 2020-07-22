package com.ley.configuration;


import com.ley.configuration.pojo.MqConfigProperty;
import com.ley.configuration.pojo.MqTypeEnum;
import com.ley.configuration.pojo.RequestTypeEnum;
import com.ley.constant.MqDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

import static com.ley.constant.Constant.*;

@Slf4j
@Configuration
public class MyConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(Environment environment) {
		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
		Binder binder = new Binder(sources);
		BindResult<Map> bindResult = binder.bind(SPRING_CLOUD_STREAM_BINDING, Map.class);
		Map<String, Map> r = bindResult.get();

		r.forEach((x, y) -> {
			validateCustomizedProperty(x, y);
			MqDictionary.put(x, getMyProperty(x, y));
		});

		log.info("Framework MQ mapping:" + MqDictionary.getInstance().toString());
		log.info("=========");
	}

	private MqConfigProperty getMyProperty(String channel, Map map) {
		MqConfigProperty property = MqConfigProperty.builder()
				.channel(channel)
				.mqType(MqTypeEnum.valueOfName(map.get(MQ_TYPE_PROPERTY).toString()))
				.requestType(RequestTypeEnum.valueOfName(map.get(REQUEST_TYPE_PROPERTY).toString()))
				.table(map.get(MQ_LOG_TABLE_PROPERTY).toString())
				.build();
		return property;
	}

	private void validateCustomizedProperty(String channel, Map property) {
		if(!property.containsKey(MQ_TYPE_PROPERTY)) {
			String msg = "Framework error: mq type " + MQ_TYPE_PROPERTY + " is not defined in property file for channel" + channel;
			log.error(msg);
			throw new RuntimeException(msg);
		}

		if(!property.containsKey(REQUEST_TYPE_PROPERTY)) {
			String msg = "Framework error: request type " + REQUEST_TYPE_PROPERTY + " is not defined in property for channel" + channel;
			log.error(msg);
			throw new RuntimeException(msg);
		}

		if(!property.containsKey(MQ_LOG_TABLE_PROPERTY)) {
			String msg = "Framework error: property " + MQ_LOG_TABLE_PROPERTY + " is not defined for channel" + channel;
			log.error(msg);
			throw new RuntimeException(msg);
		}

		if(RequestTypeEnum.valueOfName(property.get(MQ_TYPE_PROPERTY).toString()) == null) {
			String msg = "Framework error: property " + MQ_TYPE_PROPERTY + " is not supported by framework for channel" + channel;
			log.error(msg);
			throw new RuntimeException(msg);
		}

		if(RequestTypeEnum.valueOfName(property.get(REQUEST_TYPE_PROPERTY).toString()) == null) {
			String msg = "Framework error: property " + REQUEST_TYPE_PROPERTY + " is not supported by framework for channel" + channel;
			log.error(msg);
			throw new RuntimeException(msg);
		}
	}
}
