package com.lei.byImport;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportConfigurationSelector.class)
public @interface EnableMyImport {
	enum InterceptMode {
		PROXY_SCHEDULER,
		PROXY_METHOD
	}
	// just for enum testing
	InterceptMode interceptMode()default InterceptMode.PROXY_METHOD;
}
