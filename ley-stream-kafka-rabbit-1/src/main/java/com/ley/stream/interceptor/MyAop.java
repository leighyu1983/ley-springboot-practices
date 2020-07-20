package com.ley.stream.interceptor;

import com.ley.annotation.MqKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
@Aspect
public class MyAop {
	@Pointcut("@annotation(org.springframework.cloud.stream.annotation.StreamListener)")
	public void pointcut() {

	}

	@Around("pointcut()")
	public Object listenerAround(ProceedingJoinPoint pjp) throws Throwable {
		//MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint)pjp;
		//MethodSignature methodSignature = (MethodSignature)methodPoint.getSignature();;

		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		Object[] args = pjp.getArgs();

		StreamListener sl = methodSignature.getMethod().getAnnotation(StreamListener.class);
		String channel = sl.value();
		log.debug("input channel is: " + channel);

		for(Object arg : args) {
			if(arg instanceof Message) {
				Message obj = (Message) arg;
				Object payload = obj.getPayload();
				log.debug("input payload is: " + payload.toString());

				for(Field field : payload.getClass().getDeclaredFields()) {
					MqKey mqKey = field.getAnnotation(MqKey.class);
					if(mqKey != null) {
						log.debug("input payload found unique key: " + field.getName());
					}
				}

			}
		}

		//Arrays.stream(objs).forEach(System.out::print);
		//idempotentProvider.insert("t_sent", );
		return pjp.proceed();
	}
}
