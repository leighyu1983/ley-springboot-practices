package com.ley;

import com.lei.byImport.EnableMyImport;
import com.lei.entity.Interests;
import com.lei.byPassInPackage.MyMapperScanAnnotation;
import com.lei.entity.School;
import com.ley.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MyMapperScanAnnotation(basePackages = {"com.lei"}) // import by specifying package by user.
@EnableMyImport // import other package's bean automatically without spring.factories, throws bean not found exception without this import.
public class Application {


	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Application.class, args);
		Person person = (Person) ac.getBean("person");
		System.out.println("===打印结果" + person.toString());

		School school = (School) ac.getBean("school");
		System.out.println("===打印结果" + school.toString());

		Interests interests = (Interests) ac.getBean("interests");
		System.out.println("===打印结果" + interests.toString());


	}
}


