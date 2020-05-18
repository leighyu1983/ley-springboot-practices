package com.ley.entity;


import com.ley.listener.PropertyChangeEvent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LeyStringValueCollection implements PropertyChangeEvent {
	// placehoder: LeyStringValue
	private Map<String, LeyStringValue> valueStorage = new HashMap<>();

	public void add(LeyStringValue leyStringValue) {
		valueStorage.put(leyStringValue.getPlaceHolder(), leyStringValue);
	}

	public LeyStringValue get(String placeHolder) {
		return valueStorage.get(placeHolder);
	}

	private static LeyStringValueCollection INSTANCE;
	private LeyStringValueCollection() {
		// singleton
	}

	public static LeyStringValueCollection getManager() {
		if(INSTANCE == null) {
			synchronized (LeyStringValueCollection.class) {
				if(INSTANCE == null) {
					INSTANCE = new LeyStringValueCollection();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public void onChange(String placeHolder, String newValue) {
		LeyStringValue leyStringValue = this.get(placeHolder);
		Field field = leyStringValue.getField();
		boolean accessible = field.isAccessible();
		try {
			field.setAccessible(true);
			field.set(leyStringValue.getBean(), newValue);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		field.setAccessible(accessible);
	}
}
