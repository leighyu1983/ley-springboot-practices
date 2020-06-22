package com.ley.agent.rules.handler;

public interface IHandler {
	/**
	 * 方法调用前处理
	 * @return
	 */
	void before(String className,String methodName, Object[] allArguments,Object[] extVal);
	/**
	 * 方法执行完处理
	 * @return
	 */
	Object after(String className,String methodName, Object[] allArguments, Object result,Throwable t,Object[] extVal);
}
