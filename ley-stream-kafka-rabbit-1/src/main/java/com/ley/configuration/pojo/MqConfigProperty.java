package com.ley.configuration.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MqConfigProperty {
	private String channel;
	/**
	 * Record table
	 */
	private String table;
	private MqTypeEnum mqType;
	private RequestTypeEnum requestType;
}
