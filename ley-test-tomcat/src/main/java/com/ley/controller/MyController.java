package com.ley.controller;

import com.ley.util.MyCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;


/**
 * @author Leigh Yu
 * @date 2020/2/16 18:53
 */
@Slf4j
@RestController
public class MyController {

    @Autowired private RestTemplate restTemplate;

    @GetMapping("/hi")
    public String test() throws Exception {
        MyCounter.add();
        Long i = MyCounter.get();
        log.info("info log....start hi...." + i);
        String r = restTemplate.getForObject("http://localhost:8081/word?t=" + i, String.class);
        //String r = "success...";
        log.info("info log....end hi...." + r + "......." + MyCounter.get());
        return r;
    }

    private class Tickets<String> implements  Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            System.out.println("...calling...");
            return null;
        }
    }

    @GetMapping("/cool")
    public String no() {
        Callable<String> oneCallable = new Tickets<String>();
        FutureTask<String> oneTask = new FutureTask<String>(oneCallable);
        new Thread(oneTask).start();

        System.out.println("...done...");
        /**
         * output is
         * ...done...
         * ...calling...
         */
        return "done";
    }
}
