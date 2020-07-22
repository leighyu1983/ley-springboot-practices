package com.ley.configuration.pojo;

import java.util.HashMap;
import java.util.Map;

public enum RequestTypeEnum {
	INPUT("input"), OUTPUT("output");
	private String type;
	RequestTypeEnum(String type) {
		type = type.toUpperCase();
	}

	private static final Map<String, RequestTypeEnum> MAP = new HashMap<>();
	static {
		for (RequestTypeEnum requestType : values()) {
			MAP.put(requestType.name(), requestType);
		}
	}

	public static RequestTypeEnum valueOfName(String name) {
		return MAP.get(name.toUpperCase());
	}
}