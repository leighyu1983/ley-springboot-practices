package com.ley.scenario.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MyConfig {

    @Bean("myPerson")
    public Person getPerson() {
        return new Person("Tom");
    }

    @Bean(name = "school")
    public SchoolFactoryBean schoolFactory() {
        SchoolFactoryBean factoryBean = new SchoolFactoryBean();
        factoryBean.setArea("nankai");
        HashMap<String, String> a;
        return factoryBean;
    }

    @Bean
    public School school() throws Exception {
        return schoolFactory().getObject();
    }
}


