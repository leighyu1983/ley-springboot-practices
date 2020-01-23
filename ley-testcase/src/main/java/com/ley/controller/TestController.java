package com.ley.controller;

import com.ley.dto.MyOrderObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
public class TestController {
    @RequestMapping("/")
    public String say(){
        return "Hello Spring Boot";
    }

    @GetMapping("/order")
    public String order(String name){
        log.info("this is ..." + name);

        List<MyOrderObj> objs = Arrays.asList(
                new MyOrderObj(10, "tom"),
                new MyOrderObj(30, "aom"),
                new MyOrderObj(20, null),
                new MyOrderObj(40, "jerry"),
                new MyOrderObj(8, null)
        );

        Collections.sort(objs);

        log.debug(objs.toString());
        return "Hello Spring Boot";
    }
}
