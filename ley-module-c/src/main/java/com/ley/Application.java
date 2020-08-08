package com.ley;

import com.ley.mod.ClassB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
//	public static void main(String[] args) {
//		ClassB b = new ClassB();
//		b.setName("a");
//		System.out.println(b.getName());
//	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
