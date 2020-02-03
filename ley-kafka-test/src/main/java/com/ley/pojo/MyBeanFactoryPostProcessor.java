package com.ley.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("property value" + configurableListableBeanFactory.getBeanDefinition("myBean").getPropertyValues());
        System.out.println("实例化 myBean 之前执行  MyBeanFactoryPostProcessor");
    }
}
