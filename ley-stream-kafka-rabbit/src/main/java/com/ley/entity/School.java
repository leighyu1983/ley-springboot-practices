package com.ley.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class School {
	private String name;
	private String type;
	private Date createdOn;
}
