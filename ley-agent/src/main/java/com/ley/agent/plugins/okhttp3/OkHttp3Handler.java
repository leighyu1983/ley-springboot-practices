package com.ley.agent.plugins.okhttp3;

import com.ley.agent.rules.handler.AbstractHandler;

import java.util.UUID;

public class OkHttp3Handler extends AbstractHandler {
	@Override
	public void before(String className,String methodName, Object[] allArguments,Object[] extVal) {
		try {
			System.out.println(" OkHttp3Handler before.....  className " + className + " methodName" + methodName + " allArguments:" + allArguments.length);
			if(allArguments[0] != null && allArguments[0] instanceof okhttp3.Request.Builder){
				okhttp3.Request.Builder builder = (okhttp3.Request.Builder)allArguments[0];
				builder.header("ley---header----key", UUID.randomUUID().toString());
			}
		}catch (Exception e){
			System.out.println("Exception: OkHttp3Handler-->before" + e.toString());
		}
	}

	@Override
	public Object after(String className,String methodName, Object[] allArguments, Object result, Throwable t,Object[] extVal) {
		System.out.println(" OkHttp3Handler after.....  className " + className + " methodName" + methodName);
		return result;
	}
}
