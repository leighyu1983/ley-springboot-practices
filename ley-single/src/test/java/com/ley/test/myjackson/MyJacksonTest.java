package com.ley.test.myjackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ley.myjackson.AbstractFruit;
import com.ley.myjackson.Apple;
import com.ley.myjackson.Orange;
import com.ley.threading.PrintByTwoThreadsInTurn;
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

    @Test
    public void testPrintByTwoThreadsWaitNotify() throws Exception {
        String jsonStr = "{\"appleName\": \"abd\"}";

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AbstractFruit fruit = mapper.readValue(jsonStr, AbstractFruit.class);
        if(fruit instanceof Apple) {
            log.info(".....apple....");
        } else if (fruit instanceof Orange) {
            log.info(".....orange....");
        } else {
            log.info(".....unknown....");
        }
    }
}
