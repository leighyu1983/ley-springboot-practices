package com.ltest;

import com.ley.Application;
import com.ley.service.MyHiveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class BasicTest {

	@Autowired
	private MyHiveService hiveService;

	@Test
	public void test1() throws Exception {
		hiveService.testJdbcBasicAuth();
	}

}

