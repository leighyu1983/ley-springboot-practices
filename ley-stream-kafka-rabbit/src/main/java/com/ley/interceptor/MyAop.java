package com.ley.interceptor;


import com.ley.idempotent.IdempotentProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Aspect
//@Component
public class MyAop {

	@Autowired private IdempotentProvider idempotentProvider;

	//类下的方法 @Pointcut("@within(org.springframework.cloud.stream.annotation.StreamListener)")
	// 注解 @Pointcut("@annotation(org.springframework.cloud.stream.annotation.StreamListener)")
	// execution(* package.method(..))  拦截方法

	@Pointcut("@within(org.springframework.cloud.stream.annotation.EnableBinding))")
	public void producer() { }

	@Pointcut("@annotation(org.springframework.cloud.stream.annotation.StreamListener)")
	public void receiver() { }




	@Around("producer()")
	public Object producerAround(ProceedingJoinPoint pjp) throws Throwable {
		MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint)pjp;
		Object[] objs = methodPoint.getArgs();
		//Arrays.stream(objs).forEach(System.out::print);
		//idempotentProvider.insert("t_sent", );
		return pjp.proceed();
	}

	@Around("receiver()")
	public Object receiverAround(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}
}
