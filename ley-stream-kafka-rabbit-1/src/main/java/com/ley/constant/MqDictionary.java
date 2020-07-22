package com.ley.constant;

import com.ley.configuration.pojo.MqConfigProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MqDictionary {
	private static Map<String, MqConfigProperty> instance = new HashMap<>();

	public static MqConfigProperty get(String channel) {
		return instance.get(channel);
	}

	public static void put(String channel, MqConfigProperty property) {
		instance.put(channel, property);
	}

	public static Map<String, MqConfigProperty> getInstance() {
		return Collections.unmodifiableMap(instance);
	}
}
