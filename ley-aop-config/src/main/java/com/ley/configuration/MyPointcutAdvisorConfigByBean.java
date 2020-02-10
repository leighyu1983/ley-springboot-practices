package com.ley.configuration;

import com.ley.filter.MyKafkaFilter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Aspect
 *  把当前类标识为一个切面
 *
 * @Pointcut
 *  Pointcut是织入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。
 *
 */
@Configuration
public class MyPointcutAdvisorConfigByBean {
    @Bean
    public AbstractPointcutAdvisor myAspectAdvisor() {

        return new AbstractPointcutAdvisor() {
            /**
             * 一个 method 执行前或执行后的动作
             *
             * @return
             */
            @Override
            public Advice getAdvice() {
                return null;
            }

            /**
             * 根据 method 的名字或者正则表达式去拦截一个 method
             *
             * @return
             */
            @Override
            public Pointcut getPointcut() {
                // 根据类注解，方法注解进行拦截
                return new AnnotationMatchingPointcut(MyKafkaFilter.class, MyKafkaFilter.class);
            }
        };
    }
}
