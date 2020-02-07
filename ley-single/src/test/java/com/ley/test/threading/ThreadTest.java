package com.ley.test.threading;

import com.ley.threading.PrintByTwoThreadsInTurn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Leigh Yu on 2020/2/7 14:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ThreadTest {

    @Test
    public void testPrintByTwoThreadsWaitNotify() throws Exception {
        PrintByTwoThreadsInTurn test = new PrintByTwoThreadsInTurn();
        test.runWaitNotify();
    }

    @Test
    public void testPrintByTwoThreadsCAS() throws Exception {
        PrintByTwoThreadsInTurn test = new PrintByTwoThreadsInTurn();
        test.runVolatile();
    }
}