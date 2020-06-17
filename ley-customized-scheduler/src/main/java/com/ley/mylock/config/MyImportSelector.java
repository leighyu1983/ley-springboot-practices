package com.ley.mylock.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//return new String[]{ AutoProxyRegistrar.class.getName(), MyMethodLockConfig.class.getName() };
		return new String[]{ MyMethodLockConfig.class.getName() };
	}
}
