package com.ley.aspect;

import com.ley.filter.MyKafkaFilter;
import com.ley.interceptor.KafkaFilterMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
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
@Slf4j
@Configuration
public class KafkaPointcutAdvisorConfig {
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
                return new KafkaFilterMethodInterceptor();
            }

            /**
             * 根据 class/method 的注解、名字或者正则表达式去拦截一个 method
             *
             * @return
             */
            @Override
            public Pointcut getPointcut() {
                /**
                 * 可以结合配置文件来选择用哪个类
                 *
                 * 注解切点: AnnotationMatchingPointcut 根据类注解，方法注解进行拦截
                 * 表达式切点: AspectJExpressionPointcut
                 *
                 *  类级别 (包括 方法级别)
                 *      new AnnotationMatchingPointcut(annotationType, true);
                 *  方法级别
                 *      AnnotationMatchingPointcut.forMethodAnnotation(annotationType);
                 */
                return new AnnotationMatchingPointcut(MyKafkaFilter.class, true);
            }
        };
    }
}
