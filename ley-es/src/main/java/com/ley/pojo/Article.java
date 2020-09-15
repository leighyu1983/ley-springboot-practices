package com.ley.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class Article {
	private String name;
	private String content;
	private Date cdate;
	private int category;
}
