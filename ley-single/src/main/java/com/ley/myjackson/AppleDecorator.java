package com.ley.myjackson;

import com.fasterxml.jackson.annotation.JsonCreator;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Leigh Yu
 * @date 2020/2/25 21:16
 */
public class AppleDecorator extends MyAbstractDecorator<Apple> {
    @JsonCreator
    public AppleDecorator() {
        ConcurrentHashMap c;
        ThreadLocal<String> tl = new ThreadLocal<>();
    }
}
