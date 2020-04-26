package com.ley.register;

import com.lei.component.HelloComponent;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Map;

public class LeyMapperScannerRegistrar implements ImportBeanDefinitionRegistrar,
		EnvironmentAware, ResourceLoaderAware {

	@Override
	public void registerBeanDefinitions(
			AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		//扫描注解
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(LeyMapperScan.class.getName());
		String[] basePackages = (String[]) annotationAttributes.get("basePackages");

//		//HelloScan的basePackages默认为空数组
//		if (basePackages == null || basePackages.length == 0) {
//			String basePackage = null;
//			try {
//				basePackage = Class.forName(importingClassMetadata.getClassName()).getPackage().getName();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//			basePackages = new String[] {basePackage};
//		}

		//扫描类
		ClassPathBeanDefinitionScanner scanner =
				new ClassPathBeanDefinitionScanner(registry, false);
		TypeFilter helloServiceFilter = new AssignableTypeFilter(HelloComponent.class);

		scanner.addIncludeFilter(helloServiceFilter);
		scanner.scan(basePackages);
	}

	/*
	this can be done with constructor without override like feign, which is used for creating a bean.
	 */
	@Override
	public void setEnvironment(Environment environment) {
		//String projectName = environment.getProperty("what-to-do");
		String projectName = environment.resolvePlaceholders("what-to-do");
		System.out.println(projectName);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {

	}
}
