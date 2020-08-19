package com.ley;

import com.ley.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class BasicTest {

	@Autowired
	private MyService myService;

	@Test
	public void test1() throws Exception {
		myService.test();
	}
}

