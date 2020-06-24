package com.ley.agent.rules.normal;

import net.bytebuddy.asm.Advice;

import java.util.UUID;

public class MyOkHttp3Advice {

	@Advice.OnMethodEnter //方法进入前记录开始时间
	static long enter(@Advice.Origin("#t") String className,
					  @Advice.Origin("#m") String methodName,
					  @Advice.AllArguments Object[] args) {
		System.out.println("MyOkHttp3Advice my...okhttp3...." );
		if(args != null && args.length > 0 && args[0] != null && args[0] instanceof okhttp3.Request.Builder) {
			okhttp3.Request.Builder builder = (okhttp3.Request.Builder)args[0];
			builder.header("ley", "do ci da ci" + UUID.randomUUID().toString());
		}
		return System.currentTimeMillis();
	}

//	//方法退出后，结算运行时间，并按照格式输出
//	@Advice.OnMethodExit(onThrowable = Exception.class)
//	static void exit(
//			// @Advice.OnMethodEnter return value
//			@Advice.Enter long startTime,
//			@Advice.Return(typing= Assigner.Typing.DYNAMIC) Object result,
//			@Advice.Origin Method method,
//			@Advice.Thrown Throwable t) {
//
//		System.out.println(MessageFormat.format("okhttp3-----class:{0}, method:{1}, time-cost:{2}, exception:{3}",
//				method.getDeclaringClass(),
//				method.getName(),
//				System.currentTimeMillis() - startTime,
//				t == null ? "" : t.getMessage()));
//	}
}
