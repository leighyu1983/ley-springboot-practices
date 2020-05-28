package com.ley.entity;

public class User {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	@Override
	public String toString() {
		return "User: name is " + this.name;
	}
}
