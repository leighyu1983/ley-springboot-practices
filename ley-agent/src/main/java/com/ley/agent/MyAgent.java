package com.ley.agent;

import com.ley.agent.plugins.okhttp3.OkHttp3xPlugin;
import com.ley.agent.rules.normal.MyAdvice;
import com.ley.agent.rules.normal.MyOkHttp3Advice;
import com.ley.agent.rules.plugin.IInterceptPoint;
import com.ley.agent.rules.plugin.IPlugin;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;

public class MyAgent {
	/**
	 * JVM way, -javaagent:xxx.jar
	 *
	 * @param agentArgs
	 * @param inst
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("agent 1 === " + agentArgs);
		//byteBuddyAgent(inst);
		//try2(inst);
		try3(inst);
	}

	/**
	 * Attach
	 * @param agentArgs
	 */
	public static void premain(String agentArgs) {
		System.out.println("agent 2 === " + agentArgs);
	}

	/**
	 * ok for cutting controller's methods with/without proxy aop methods.
	 * @param inst
	 */
	private static void try2(Instrumentation inst) {
		new AgentBuilder.Default()
			// ignore packages
			.ignore(ElementMatchers.nameStartsWith("com.ley.agent")
					.or(ElementMatchers.nameStartsWith("com.ley.agent")))
			.type(ElementMatchers.nameStartsWithIgnoreCase("com.ley"))
			// define transform rules
			.transform((builder, typeDescription, classLoader, module) ->
				//builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.isAnnotatedWith(ElementMatchers.named("com.TimeLog")))))
				//  to avoid constructor exception by adding "on(ElementMatchers.isMethod())"
				builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.isMethod())))
			//
			.with(new AgentBuilder.Listener(){
				@Override
				public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
				}

				@Override
				public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
				}

				@Override
				public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
				}

				@Override
				public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
					System.out.println("ERROR......" + throwable.getMessage());
				}

				@Override
				public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
				}
			})
			//
			.installOn(inst);
	}

	/**
	 * ok for cutting controller's methods with/without proxy aop methods.
	 * @param inst
	 */
	private static void try3(Instrumentation inst) {

		//DO NOT ADD message in listener since need more testing. Agent doesn't take effect due to this reason.
		AgentBuilder agentBuilder = new AgentBuilder.Default();

		// rules okhttp
		agentBuilder = agentBuilder
			.type(ElementMatchers.named("okhttp3.Request"))
			.transform((builder, typeDescription, classLoader, module) ->
				builder.visit(Advice.to(MyOkHttp3Advice.class).on(ElementMatchers.isConstructor()))
			);

		// rules method, this can handle the situation that proxy cannot handle, a->b->c
		agentBuilder = agentBuilder
			.type(ElementMatchers.nameStartsWithIgnoreCase("com.ley"))
			.transform((builder, typeDescription, classLoader, module) ->
					builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.isMethod()))
			);

		agentBuilder.with(buildListener()).installOn(inst);
	}

	private static void byteBuddyAgent(Instrumentation inst) {
		System.out.println(" in byteBuddyAgent  ");
		AgentBuilder agentBuilder = buildAgentBuilder();
		System.out.println(" finish agentBuilder  ");

		List<IPlugin> plugins = loadPlugins();
		for(IPlugin plugin : plugins) {
			for (IInterceptPoint point : plugin.buildInterceptPoint()) {
				System.out.println("Looping....IInterceptPoint" + point.getClass());
				AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
					@Override
					public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
						String className = typeDescription.getCanonicalName();
						//builder = builder.visit(Advice.to(plugin.interceptorAdviceClass()).on(point.buildMethodsMatcher()));
						builder = builder.visit(Advice.to(plugin.interceptorAdviceClass()).on(ElementMatchers.any()));
						return builder;
					}
				};
				agentBuilder = agentBuilder.type(point.buildTypesMatcher()).transform(transformer);//.asTerminalTransformation();
			}
		}


		System.out.println(" finish appendTransformerFromPlugin  ");
		AgentBuilder.Listener listener = buildListener();
		System.out.println(" finish listener  ");
		agentBuilder.with(listener).installOn(inst);
		System.out.println(" finish last step  ");
	}

	private static AgentBuilder buildAgentBuilder() {
		return new AgentBuilder.Default().ignore(ElementMatchers.nameStartsWith("com.ley.agent."));
		//return new AgentBuilder.Default();
	}

	private static AgentBuilder.Listener buildListener() {
		return new AgentBuilder.Listener() {
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
				System.out.println("ERROR......" + s + "   MESSAGE:  " + throwable.getMessage());
			}

			@Override
			public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

			}
		};
	}

	private static List<IPlugin> loadPlugins() {
		return Arrays.asList(new OkHttp3xPlugin());
	}
}
