package com.ley;

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
	}

	/**
	 * Attach
	 * @param agentArgs
	 */
	public static void premain(String agentArgs) {
		System.out.println("agent 2 === " + agentArgs);
	}
}
