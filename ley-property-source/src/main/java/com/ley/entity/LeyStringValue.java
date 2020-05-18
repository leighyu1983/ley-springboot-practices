package com.ley.entity;

import java.lang.reflect.Field;

public class LeyStringValue {
	private String beanName;
	private Object bean;
	private Field field;
	private String placeHolder;

	public LeyStringValue(String placeHolder, Object bean, String beanName, Field field) {
		this.beanName = beanName;
		this.bean = bean;
		this.field = field;
		this.placeHolder = placeHolder;
	}

	public String getPlaceHolder() {
		return this.placeHolder;
	}

	public Field getField() {
		return this.field;
	}

	public Object getBean() {
		return bean;
	}
}
