package com.ley.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    AutowiredAnnotationBeanPostProcessor a;
    // BeanPostProcessor
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //if(beanName.contains("getBean4")) {
            System.out.println("MyBeanPostProcessor...postProcessBeforeInitialization--->" + beanName);
        //}
        return bean;
    }

    // BeanPostProcessor
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //if(beanName.contains("getBean4")) {
            System.out.println("MyBeanPostProcessor...postProcessAfterInitialization--->" + beanName);
        //}
        return bean;
    }
}