package com.ley.stream.entity;

import com.ley.annotation.MqKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class School {
	@MqKey
	private String id;
	private String name;
	private Date cdate;
}

