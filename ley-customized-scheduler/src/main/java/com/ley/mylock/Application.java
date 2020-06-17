package com.ley.mylock;


import com.ley.mylock.annotat.EnableWowMyScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableWowMyScheduler
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
