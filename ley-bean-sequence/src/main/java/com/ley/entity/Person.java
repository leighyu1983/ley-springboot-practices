package com.ley.entity;

public class Person {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	@Override
	public String toString() {
		return "Person: name is " + this.name;
	}
}
