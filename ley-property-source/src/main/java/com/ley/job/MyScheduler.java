package com.ley.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyScheduler {

	@Scheduled(cron = "1 * * * * *")
	//@Scheduled(fixedRate = 1000 * 1)
	public void jobPrint() {
		System.out.println(new Date() + " printing.....");
	}

}
