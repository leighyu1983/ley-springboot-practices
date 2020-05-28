package com.ley.shedlock.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyScheduler {
	@Scheduled(cron = "0/1 * * * * ? ")
	@SchedulerLock(name = "scheduledTaskName", lockAtMostFor = "30s")
	public void myTask() {
		log.info("running.....");
	}
}
