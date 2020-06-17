package com.ley.processor;


import com.ley.entity.LeyStringValue;
import com.ley.entity.LeyStringValueCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


@Component
public class MySpringValueProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {

		processConfigurationPropertyField(bean, beanName);

		for (Field field : findAllField(bean.getClass())) {
			processValueField(bean, beanName, field);
		}
		return bean;
	}

	/**
	 *
	 * @param bean
	 * @param beanName
	 */
	private void processConfigurationPropertyField(Object bean, String beanName) {
		ConfigurationProperties cp = (ConfigurationProperties) bean.getClass().getAnnotation(ConfigurationProperties.class);

		/**
		 *
		 *	  TODO if this is encapused in another jar/starter, business application package name
		 *	  TODO should be passed in to jar/starter pass the package into current place.
		 *    TODO not support Bean ....
		 * 	  TODO class's Field, below is method.
		 *
		 *     @Bean
		 *     @ConfigurationProperties(prefix = "custom.source")
		 *     public DataSource dataSource() {
		 *         return DataSourceBuilder.create().build();
		 *     }
		 *
		 *     @Bean
		 *     public JdbcTemplate jdbcTemplate() {
		 *         return new JdbcTemplate(dataSource());
		 *     }
 		 */
		if(cp == null || !bean.getClass().getPackage().getName().contains("com.ley")) {
			return ;
		}

		List<Field> fields = findAllField(bean.getClass());

		for(Field field : fields) {
			processConfigPropertiesField(bean, beanName, field);
		}
	}

	private List<Field> findAllField(Class clazz) {
		final List<Field> res = new LinkedList<>();
		ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				res.add(field);
			}
		});
		return res;
	}

	protected void processValueField(Object bean, String beanName, Field field) {
		Value value = field.getAnnotation(Value.class);
		if (value == null) {
			return;
		}

		String placeHolder = value.value();
		placeHolder = placeHolder.replace("$", "").replace("{", "").replace("}", "");
		LeyStringValueCollection.getManager().add(new LeyStringValue(placeHolder, bean, beanName, field));
		String qq = "";
	}

	protected void processConfigPropertiesField(Object bean, String beanName, Field field) {
		LeyStringValueCollection.getManager().add(new LeyStringValue(field.getName(), bean, beanName, field));
		String qq = "";
	}
}
