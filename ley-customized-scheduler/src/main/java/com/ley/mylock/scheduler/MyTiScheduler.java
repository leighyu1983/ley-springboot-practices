package com.ley.mylock.scheduler;

import com.ley.mylock.annotat.WowSchedulerLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MyTiScheduler {

	@Scheduled(cron = "0/1 * * * * ? ")
	@WowSchedulerLock(name = "lei....", lockAtMostFor = "30s")
	public void myTiTask() {
		log.info("running...my...ti...task..." + Thread.currentThread().getName());
	}

	@Scheduled(cron = "0/1 * * * * ? ")
	@WowSchedulerLock(name = "lei....", lockAtMostFor = "30s")
	public void myT2Task() {
		log.info("running...task....2..." + Thread.currentThread().getName());
	}
}
