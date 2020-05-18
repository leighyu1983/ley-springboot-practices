package com.ley.processor;


import com.ley.entity.LeyStringValue;
import com.ley.entity.LeyStringValueCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


@Component
public class MySpringValueProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		for (Field field : findAllField(bean.getClass())) {
			processField(bean, beanName, field);
		}
		return bean;
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

	protected void processField(Object bean, String beanName, Field field) {
		Value value = field.getAnnotation(Value.class);
		if (value == null) {
			return;
		}

		String placeHolder = value.value();
		placeHolder = placeHolder.replace("$", "").replace("{", "").replace("}", "");
		LeyStringValueCollection.getManager().add(new LeyStringValue(placeHolder, bean, beanName, field));
		String qq = "";
	}
}
