package com.ley.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
@Configuration
public class MyBean implements InitializingBean {

    private String desc = "初始值";

    public MyBean() {
        System.out.println("......myBean 构造函数.....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("myBean 调用afterPropertiesSet方法");
        this.desc = "在初始化方法中修改之后的描述信息";
    }


    @Bean(initMethod="initNotManageBySpring")
    public MyBean2 initMethod(){
        return new MyBean2();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("myBean 调用postConstruct方法");
    }

    @Override
    public String toString() {
        return "myBean的toString方法：" + this.desc;
    }
}
