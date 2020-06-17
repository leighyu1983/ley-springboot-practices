package com.ley.mylock.scheduler;

import com.ley.mylock.db.exection.MyLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;

@Configuration
public class WowSchedulerConfig {
	@Bean
	public MyLockProvider myLockProvider(DataSource dataSource) throws Exception {
		return new MyLockProvider(dataSource);
	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
		executor.setPoolSize(20);
		executor.setThreadNamePrefix("leyTask-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		return executor;
	}
}
