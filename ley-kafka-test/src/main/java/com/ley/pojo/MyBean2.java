package com.ley.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class MyBean2 implements InitializingBean, BeanPostProcessor, BeanFactoryPostProcessor {

    public MyBean2() {
        System.out.println("调用 construct....");
    }

    /**
     * initMethod
     */
    public void initNotManageBySpring() {
        System.out.println("调用initMethod方法,g MyBean2");
    }

    @PostConstruct
    public void test() {
        System.out.println("调用 PostConstruct()");
    }

    // InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用 afterPropertiesSet...");
    }

    // BeanFactoryPostProcessor
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("property value" + beanFactory.getBeanDefinition("myBean2").getPropertyValues());
        System.out.println("调用 postProcessBeanFactory");
    }

    // BeanPostProcessor
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("调用 postProcessBeforeInitialization...");
        return bean;
    }
}
