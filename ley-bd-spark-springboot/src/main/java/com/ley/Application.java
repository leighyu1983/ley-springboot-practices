package com.ley;

import com.ley.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**

 ./spark-submit --class com.ley.Application --master local \
 /ley/testdata/ley-bd-spark-springboot-2.3.2.RELEASE.jar


 ./spark-submit --class com.ley.Application --master local[2] \
 /ley/testdata/ley-bd-spark-springboot-2.3.2.RELEASE.jar

 */
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class Application implements CommandLineRunner {

	@Autowired private MyService myService;

	static {
		System.setProperty("HADOOP_USER_NAME", "root");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		myService.simpleCompute();
	}
}
