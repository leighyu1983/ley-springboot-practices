package com.ley.agent.plugins.okhttp3;

import com.ley.agent.rules.handler.IHandler;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

public class OkHttp3xAdvice {
//	@Advice.OnMethodEnter()
//	public static void enter(@Advice.Local("handler") IHandler handler,
//							 @Advice.Origin("#t") String className,
//							 @Advice.Origin("#m") String methodName,
//							 @Advice.AllArguments Object[] args){
//		handler = new OkHttp3Handler();
//		handler.before(className,methodName,args,null);
//	}

	@Advice.OnMethodEnter
	public static void enter(@Advice.AllArguments Object args[], @Advice.Origin Method method) {
		System.out.println("OkHttp3xAdvice...enter...method name:" + method.getName() + "  class:" + method.getClass().getName());
	}
}
