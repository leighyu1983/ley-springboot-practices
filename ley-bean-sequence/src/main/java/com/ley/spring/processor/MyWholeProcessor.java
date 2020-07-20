package com.ley.spring.processor;

import com.ley.stream.entity.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * AbstractApplicationContext
 *   refresh()
 *     // 扫描bean并注入
 *     invokeBeanFactoryPostProcessors
 *         ConfigurationClassPostProcessor -> processConfigBeanDefinitions
 *         (在DefaultListableBeanFactory 的 put方法添加断电，可以找到如下追踪)
 *         A. 扫描类包，加载含有@Component注解的类：ClassPathBeanDefinitionScanner.doScan(...)调用的其他类。
 *           1. 根据basepackage字符串,找到package目录下，包括子目录在内的全部类文件放入Resource[]中。
 *           2. 根据Resource[]找到@Component修饰的类，封装成BeanDefinition集合返回。
 *              遍历Resource[]中的文件，如果文件含有@Component注解，则创建BeanDefinition(ScannedGenericBeanDefinition)对象。
 *              此时BeanDefinition只设置了beanClass(当前被扫描的类文件的类)和resource(class文件位置)。
 *           3. 根据BeanDefinition的metaData(就是类)上的注解@Scope的值，默认为Singleton (ScopeMetadata中的默认值)
 *           4. 注入BeanDefinition到DefaultListableBeanFactory(BeanDefinition的存储位置)
 *
 *         B. 处理@Import注解
 *           1. 依次解析BeanDefinition, 处理含有@Import注解的类的导入，ParserStrategyUtils.instantiateClass
 *              通过ClassLoader或者ResourceLoader加载@import修饰的类文件,并且通过createInstance实例化该类
 *              通过ParseStrategyUtils.invokeAwareMethods(), 如果实现了Aware接口，就依次回调该实例实现的各种Aware接口的set方法.
 *         A. doScan下的this.reader<ConfigurationClassBeanDefinitionReader>.loadBeanDefinitions
 *           5. 加载注入的类下的@Bean注解修饰的方法的Bean
 *         ApplicationContextAwareProcessor -> postProcessBeforeInitialization
 *           6. 回调各种Aware接口
 */
@Component
public class MyWholeProcessor implements
		BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor, BeanPostProcessor,
		ApplicationContextAware, EnvironmentAware, ResourceLoaderAware {

	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("第1步 EnvironmentAware 注入 environment");
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		System.out.println("第2步 ResourceLoaderAware 注入 resourceLoader");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("第3步 ApplicationContextAware 注入 applicationContext");
	}

	/**
	 * 动态注入Bean, BeanDefinitionRegistryPostProcessor -> postProcessBeanDefinitionRegistry
	 * 通过此处注入
	 *
	 * @param registry
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(Person.class);
		builder.addPropertyValue("name", "tom.....");
		registry.registerBeanDefinition("person", builder.getBeanDefinition());
		System.out.println("第4步：BeanDefinitionRegistryPostProcessor接口实现动态注入Bean并修改Bean的属性值：");
	}

	/**
	 * BeanFactoryPostProcessor -> postProcessBeanFactory
	 * 通过@Bean注入
	 *
	 * @param beanFactory
	 * @throws BeansException
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("第5步：调用BeanFactoryPostProcessor接口的postProcessBeanFactory");
		BeanDefinition bd = beanFactory.getBeanDefinition("customBean");
		MutablePropertyValues pv =  bd.getPropertyValues();
		if (!pv.contains("remark")) {
			pv.addPropertyValue("remark", "在BeanFactoryPostProcessor中修改之后的备忘信息");
		}
	}

	/**
	 * BeanPostProcessor -> postProcessBeforeInitialization
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equals("customBean")) {
			System.out.println("第7步：BeanPostProcessor，对象" + beanName + "调用初始化方法之前的数据： " + bean.toString());
		}
		return bean;
	}

	/**
	 * BeanPostProcessor -> postProcessAfterInitialization
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equals("customBean")) {
			System.out.println("第11步：BeanPostProcessor，对象" + beanName + "调用初始化方法之后的数据：" + bean.toString());
		}
		return bean;
	}
}
