package com.ley.mylock.config;

import com.ley.mylock.annotat.WowSchedulerLock;
import com.ley.mylock.db.exection.MyLockProvider;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyMethodLockConfig {

	/**
	 * MyLockProvider is injected from client with explicitly decorated with @Bean.
	 */
	private MyLockProvider myLockProvider = null;
	public MyMethodLockConfig(MyLockProvider myLockProvider) {
		this.myLockProvider = myLockProvider;
	}

	@Bean
	public AbstractPointcutAdvisor myAdvisor() {

		return new AbstractPointcutAdvisor() {

			@Override
			public Advice getAdvice() {
				return getMyAdvice();
			}

			@Override
			public Pointcut getPointcut() {
				return getMyPointcut();
			}

			private Advice getMyAdvice() {
				return new MethodInterceptor() {
					@Override
					public Object invoke(MethodInvocation invocation) throws Throwable {
						//myLockProvider.lock();
						System.out.println("aop....." + Thread.currentThread().getName());
						return invocation.proceed();
					}
				};
			}

			private Pointcut getMyPointcut() {
				return new AnnotationMatchingPointcut(null, WowSchedulerLock.class,true);
			}
		};
	}
}
