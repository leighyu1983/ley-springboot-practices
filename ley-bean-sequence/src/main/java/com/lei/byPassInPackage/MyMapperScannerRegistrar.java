package com.lei.byPassInPackage;

import com.lei.entity.School;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Map;


public class MyMapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(
			AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		DefaultListableBeanFactory b;
		//扫描注解
		Map<String, Object> annotationAttributes = importingClassMetadata
				.getAnnotationAttributes(MyMapperScanAnnotation.class.getName());
		String[] basePackages = (String[]) annotationAttributes.get("basePackages");

		// inject School object.
		ClassPathBeanDefinitionScanner scanner =
				new ClassPathBeanDefinitionScanner(registry, false);
		TypeFilter helloServiceFilter = new AssignableTypeFilter(School.class);

		scanner.addIncludeFilter(helloServiceFilter);
		scanner.scan(basePackages);
		System.out.println("第12步：通过ImportBeanDefinitionRegistrar注入客户端指定package中的Bean,做处理");
	}
}
