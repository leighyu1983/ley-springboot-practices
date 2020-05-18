package com.ley;

import com.ley.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Application.class, args);
		Person person = (Person) ac.getBean("person");
		System.out.print("第12步打印结果" + person.toString());
	}
}
