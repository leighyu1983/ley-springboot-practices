package com.ley;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MyAgent {
	/**
	 * JVM way, -javaagent:xxx.jar
	 *
	 * @param agentArgs
	 * @param inst
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("agent 1 === " + agentArgs);
		byteBuddyAgent(inst);
	}

	/**
	 * Attach
	 * @param agentArgs
	 */
	public static void premain(String agentArgs) {
		System.out.println("agent 2 === " + agentArgs);
	}

	private static void byteBuddyAgent(Instrumentation inst) {
		AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
			@Override
			public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
													TypeDescription typeDescription,
													ClassLoader classLoader,
													JavaModule javaModule) {
				return builder
						.method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
						.intercept(MethodDelegation.to(MyAgentInterceptor.class)); // 委托
			}
		};

		AgentBuilder.Listener listener = new AgentBuilder.Listener() {
			@Override
			public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

			}

			@Override
			public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

			}

			@Override
			public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

			}

			@Override
			public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

			}

			@Override
			public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

			}
		};

		new AgentBuilder
				.Default()
				//.type(ElementMatchers.nameStartsWith("com.example.demo"))
				.type(ElementMatchers.any()) // 指定需要拦截的类
				.transform(transformer)
				.with(listener)
				.installOn(inst);
	}
}
