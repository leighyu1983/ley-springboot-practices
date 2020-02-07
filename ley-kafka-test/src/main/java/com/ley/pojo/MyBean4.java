package com.ley.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class MyBean4 implements InitializingBean {

    // only in @Bean, only in component
    public void MyBean4Init() {
        HashSet hs;
        HashMap hm;
        ReentrantLock r;
        ArrayBlockingQueue a;
        LinkedBlockingQueue b;
        System.out.println("Bean4...MyBean4Init  only in @Bean, not in component");
    }

    public MyBean4() {
        System.out.println("Bean4...consruct");
    }

    @PostConstruct
    public void postContruct() {
        System.out.println("Bean4...postConstruct");
    }

    // InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean4...afterPropertiesSet");
    }
}
