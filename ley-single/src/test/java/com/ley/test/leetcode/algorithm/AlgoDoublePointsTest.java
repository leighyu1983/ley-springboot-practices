package com.ley.test.leetcode.algorithm;

import com.ley.leetcode.algorithm.AlgoDoublePoints;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Leigh Yu
 * @date 2020/2/8 15:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AlgoDoublePointsTest {

    @Test
    public void testSumOfSquareNumbers() {
        boolean result = AlgoDoublePoints.sumOfSquareNumbers(17);
    }

    @Test
    public void testReverseVowelsOfString() {
        String result = AlgoDoublePoints.reverseVowelsOfString("leetcode");
        log.info("------>" + result);
    }
}
