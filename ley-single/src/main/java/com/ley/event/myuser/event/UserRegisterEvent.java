package com.ley.event.myuser.event;

import org.springframework.context.ApplicationEvent;

public class UserRegisterEvent extends ApplicationEvent {
	public UserRegisterEvent(String name) {
		super(name);
	}
}
