package com.ley.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ComparableBean implements Comparable<ComparableBean> {
	private String name;
	private int age;

	@Override
	public int compareTo(ComparableBean o) {
		return this.age - o.age;
	}
}
