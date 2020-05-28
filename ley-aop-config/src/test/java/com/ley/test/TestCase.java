package com.ley.test;

import com.ley.shedlock.configuration.MyConfiguration;
import com.ley.component.MyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ContextConfiguration(classes = MyConfiguration.class)
 * @Autowired MyService myService;
 *
 * Equals to
 *
 * AbstractApplicationContext c = new AnnotationConfigApplicationContext(MyConfiguration.class);
 * c.getBean("myService")
 */
@Slf4j
@ContextConfiguration(classes = MyConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCase {

    @Autowired MyService myService;

    @Test
    public void test() {
        String name = myService.getName();
        log.debug("。。。。。。。。。。。。。。。。。。。。。。。。。。test case one's name is -> {}", name);
        log.info("。。。。。。。。。。。。。。。。。。。。。。。。。。test case one's name is -> {}", name);
    }
}
