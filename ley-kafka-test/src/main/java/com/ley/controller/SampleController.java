package com.ley.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
public class SampleController {

    // local
    private ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>(1000);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send/{topic}/{message}")
    public String send(@PathVariable String topic, @PathVariable String message) {
        log.debug(" debug.........send to kafka topic {}, message {}", topic, message);
        log.info(" info.........send to kafka topic {}, message {}", topic, message);
        ListenableFuture<SendResult<String, Object>> result = kafkaTemplate.send(topic, message);
        return "ok";
    }
}
