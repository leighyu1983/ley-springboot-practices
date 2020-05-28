package com.ley.scenario.serialization;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SerializeObjWithSerialization implements Serializable {
	//private long serialVersionUID = 2L;
	private long serialVersionUID = 893475938476L;

	private String name;
	private int age;

	public void eat() {
		System.out.println("eating apple.....");
	}
}
