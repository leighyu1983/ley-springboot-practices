package com.ley.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hi")
public class MyPropertyConfig {
	// exist
	private String you;
	// not exist - debug ignoreUnknownFields = true
	private String he;

	public String getYou() {
		return this.you;
	}

	public void setYou(String you) {
		this.you = you;
	}
}
