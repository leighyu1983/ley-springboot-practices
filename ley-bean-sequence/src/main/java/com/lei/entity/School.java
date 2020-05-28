package com.lei.entity;

public class School {
	public School() {
		System.out.println("initializing school entity");
	}
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	@Override
	public String toString() {
		return "School: name is " + this.name;
	}
}
