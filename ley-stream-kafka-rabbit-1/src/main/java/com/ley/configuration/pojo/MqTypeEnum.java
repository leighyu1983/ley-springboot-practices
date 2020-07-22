package com.ley.configuration.pojo;

import java.util.HashMap;
import java.util.Map;

public enum MqTypeEnum {
	RABBIT("rabbit"), KAFKA("kafka");

	private String mq;
	MqTypeEnum(String mq) {
		mq = mq.toUpperCase();
	}

	private static final Map<String, MqTypeEnum> MAP = new HashMap<>();
	static {
		for (MqTypeEnum mqType : values()) {
			MAP.put(mqType.name(), mqType);
		}
	}

	public static MqTypeEnum valueOfName(String name) {
		return MAP.get(name.toUpperCase());
	}
}
