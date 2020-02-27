package com.ley.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author Leigh Yu
 * @date 2020/2/22 22:03
 */
public class KafkaFilterMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        KafkaListener listener = methodInvocation.getMethod().getDeclaredAnnotation(KafkaListener.class);
        String id = listener.groupId();

        Object result = methodInvocation.proceed();
        return result;
    }
}
