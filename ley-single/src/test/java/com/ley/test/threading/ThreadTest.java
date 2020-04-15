package com.ley.test.threading;

import com.ley.threading.PrintByTwoThreadsInTurn;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Leigh Yu on 2020/2/7 14:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThreadTest {

    private PrintByTwoThreadsInTurn test = new PrintByTwoThreadsInTurn();

    @Test
    public void testPrintByTwoThreadsWaitNotify() throws Exception {
        test.runWaitNotify();
    }

    @Test
    public void testPrintByTwoThreadsCAS() throws Exception {
        test.runVolatile();
    }

    @Test
    public void testRunReentrantCondition() throws Exception {
        test.runReentrantCondition();
    }

    @Test
    public void testRunFutureTask() throws Exception {
        test.runFutureTask();
    }

    @Test
    public void testMilli() {
        int taskSize = 100;
        int taskQueueSize = taskSize;

        CyclicBarrier cb = new CyclicBarrier(6);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                6, 6, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(taskQueueSize));


        for(int i = 0; i < taskSize; i++) {
            executor.submit(() -> {
                try {
                    cb.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis());
            });
        }
    }
}