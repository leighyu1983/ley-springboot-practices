package com.ley.db.spark.entity;

public class User {
	public User(int id, String name, long phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	private int id;
	private String name;
	private long phone;

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", phone=" + phone +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}
}
