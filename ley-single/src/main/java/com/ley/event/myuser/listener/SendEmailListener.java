package com.ley.event.myuser.listener;

import com.ley.event.myuser.event.UserRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener implements ApplicationListener<UserRegisterEvent> {
	@Override
	public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
		System.out.println("邮件服务接到通知，给 " + userRegisterEvent.getSource() + " 发送邮件...");
	}
}