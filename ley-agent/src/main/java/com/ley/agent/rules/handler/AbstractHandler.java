package com.ley.agent.rules.handler;


public abstract class AbstractHandler implements IHandler {

	public void logBeginTrace(String className,String methodName){
		System.out.println("className='" + className + "' methodName='" + methodName + "'");
	}

	public void logEndTrace(String className, String methodName){
		System.out.println("className='" + className + "' methodName='" + methodName + "'");
	}

	public void calculateSpend(){

	}
}
