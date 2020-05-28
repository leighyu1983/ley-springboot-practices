package com.lei.entity;


public class Interests {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	@Override
	public String toString() {
		return "Interests: name is " + this.name;
	}
}

