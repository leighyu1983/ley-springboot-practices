package com.ley.mod;

import com.ley.module.ClassA;

public class ClassB {
	private String name;
	private ClassA classA;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setClassA(ClassA classA) {
		this.classA = classA;
	}

	public ClassA getClassA() {
		return this.classA;
	}

	@Override
	public String toString() {
		return this.name + " ---> " + this.getClassA().getName();
	}
}
