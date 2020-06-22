package com.ley.agent.rules.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * 拦截点入口
 */
public interface IInterceptPoint {
	/**
	 * 类匹配规则
	 * @return
	 */
	ElementMatcher<TypeDescription> buildTypesMatcher();

	/**
	 * 方法匹配规则
	 * @return
	 */
	ElementMatcher<MethodDescription> buildMethodsMatcher();
}
