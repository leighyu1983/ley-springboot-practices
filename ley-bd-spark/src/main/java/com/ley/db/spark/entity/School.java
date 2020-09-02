package com.ley.db.spark.entity;

import java.io.Serializable;

public class School implements Serializable {
	public School(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
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

	private String name;
	private String pwd;

	@Override
	public String toString() {
		return "name:" + name + "  pwd:" + pwd;
	}
}


