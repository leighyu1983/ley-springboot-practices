package com.ley.agent.rules.normal;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Method;
import java.text.MessageFormat;

public class MyAdvice {
	@Advice.OnMethodEnter //方法进入前记录开始时间
	static long enter(@Advice.AllArguments Object args[], @Advice.Origin Method method) {
		System.out.println("MyAdvice start time...." + System.currentTimeMillis());
		return System.currentTimeMillis();
	}

	//方法退出后，结算运行时间，并按照格式输出
	@Advice.OnMethodExit(onThrowable = Exception.class)
	static void exit(
			// @Advice.OnMethodEnter return value
			@Advice.Enter long startTime,
			@Advice.Return(typing= Assigner.Typing.DYNAMIC) Object result,
			@Advice.Origin Method method,
			@Advice.Thrown Throwable t) {

		System.out.println(MessageFormat.format("MyAdvice {0}, method:{1}, time-cost:{2}, exception:{3}",
				method.getDeclaringClass(),
				method.getName(),
				System.currentTimeMillis() - startTime,
				t == null ? "" : t.getMessage()));
	}
}
