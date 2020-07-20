package com.ley.stream.entity;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class CustomBean implements InitializingBean {
	private String desc;
	private String remark;

	public CustomBean() {
		System.out.println("第6步：执行CustomBean类的无参构造函数");
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@PostConstruct
	public void postConstruct() {
		System.out.println("第8步：调用postConstruct方法");
	}
	public void afterPropertiesSet() throws Exception {
		System.out.println("第9步：调用afterPropertiesSet方法");
		this.desc = "在初始化方法中修改之后的描述信息";
	}
	public void initMethod() {
		System.out.println("第10步：调用initMethod方法");
	}
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[描述：").append(desc);
		builder.append("， 备注：").append(remark).append("]");
		return builder.toString();
	}
}