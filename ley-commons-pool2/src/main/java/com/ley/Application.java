package com.ley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * java -javaagent:<path>/myAgent.jar -jar myApp.jar
 *
 * http://www.imooc.com/article/302070
 * https://bugstack.cn/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html
 *
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
