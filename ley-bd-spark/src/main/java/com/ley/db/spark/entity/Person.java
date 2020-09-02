package com.ley.db.spark.entity;

import java.io.Serializable;

public class Person implements Serializable {
	public Person(String name, String pwd, int year) {
		this.name = name;
		this.pwd = pwd;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	private String name;
	private String pwd;
	private int year;

	@Override
	public String toString() {
		return "name:" + name + "  pwd:" + pwd + " year:" + year;
	}
}
