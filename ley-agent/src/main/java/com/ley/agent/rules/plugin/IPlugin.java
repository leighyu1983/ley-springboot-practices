package com.ley.agent.rules.plugin;

import java.util.List;

public interface IPlugin {
	String getName();
	List<IInterceptPoint> buildInterceptPoint();
	Class interceptorAdviceClass();
}
