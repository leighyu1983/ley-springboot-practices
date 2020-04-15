package com.ley.test.myjackson;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leigh Yu
 * @date 2020/2/25 20:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class MyJacksonTest {

    //防止在极端情况下的指令重排
    private static volatile MyJacksonTest instance;
    private MyJacksonTest(){ };

    public static MyJacksonTest getInstance() {
        // 优化锁范围
        if (instance == null) {
            // 防止多线程竞争资源
            synchronized (MyJacksonTest.class) {
                /*
                  防止第一个线程为检查实例为null后，
                  第二个线程进入检查也为null,并且完成了初始化。
                  此时第一个线程在这里需要在判断实例是否为空，否则会实例化两个实例
                 */
                if (instance == null) {
                    instance = new MyJacksonTest();
                }
            }
        }
        return instance;
    }

    @Test
    public void testPrintByTwoThreadsWaitNotify() throws Exception {
        String jsonStr = "{\"appleName\": \"abd\"}";


    }
}
