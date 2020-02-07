package com.ley.test.algorithm;

import com.ley.algorithm.AlgoSimple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Leigh Yu
 * @date 2020/2/7 19:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlgoSimpleTest {

    @Test
    public void testReverseString() {
        AlgoSimple.reverseString();
    }
}
