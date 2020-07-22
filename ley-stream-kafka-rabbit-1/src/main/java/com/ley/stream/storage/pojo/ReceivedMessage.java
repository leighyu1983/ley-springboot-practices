package com.ley.stream.storage.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ReceivedMessage {
	private String channel;
	private String header;
	private String data;
	private Date cdate;
	private Date edate;
	private String creator;
	private String editor;
	private String uniqueFieldValue;
	private String traceId;
}
