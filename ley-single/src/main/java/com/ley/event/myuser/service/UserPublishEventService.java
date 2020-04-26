package com.ley.event.myuser.service;

import com.ley.event.myuser.event.UserRegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class UserPublishEventService implements ApplicationEventPublisherAware {
	public void register(String name) {
		System.out.println("用户：" + name + " 已注册！");
		applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
	}

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
