package com.ley.service;

import com.ley.filter.MyKafkaConsumerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyService {

    @MyKafkaConsumerFilter
    public String getName() {
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return "hello world";
    }
}
