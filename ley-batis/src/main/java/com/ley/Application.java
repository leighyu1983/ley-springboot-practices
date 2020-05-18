package com.ley;

import com.ley.register.LeyMapperScan;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@LeyMapperScan({"com.lei.component"})
public class Application {
	public static void main(String[] args) {
		BeanPostProcessor a;
		BeanFactoryPostProcessor b;
		SpringApplication.run(Application.class, args);
	}
}
