package com.ley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadPoolExecutor;


@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ThreadPoolExecutor as;
		SpringApplication.run(Application.class, args);
	}
}