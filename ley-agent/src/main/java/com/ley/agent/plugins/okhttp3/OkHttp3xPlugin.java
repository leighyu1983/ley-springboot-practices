package com.ley.agent.plugins.okhttp3;

import com.ley.agent.rules.plugin.IInterceptPoint;
import com.ley.agent.rules.plugin.IPlugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Arrays;
import java.util.List;

public class OkHttp3xPlugin implements IPlugin {
	@Override
	public String getName() {
		return "okhttp3x";
	}

	@Override
	public List<IInterceptPoint> buildInterceptPoint() {
		System.out.println(" IPlugin buildInterceptPoint.....  ");

		return Arrays.asList(
				new IInterceptPoint() {
					@Override
					public ElementMatcher<TypeDescription> buildTypesMatcher() {
						System.out.println(" IPlugin buildTypesMatcher.....  ");
						//return ElementMatchers.named("okhttp3.Request");
						return ElementMatchers.nameContainsIgnoreCase("okhttp");
					}

					@Override
					public ElementMatcher<MethodDescription> buildMethodsMatcher() {
						System.out.println(" IPlugin buildMethodsMatcher.....  ");
						return ElementMatchers.isConstructor();
					}
				}
		);
	}

	@Override
	public Class interceptorAdviceClass() {
		System.out.println(" IPlugin interceptorAdviceClass.....  ");

		return OkHttp3xAdvice.class;
	}
}
