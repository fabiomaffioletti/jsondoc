package org.jsondoc.core.util;

import java.lang.reflect.Field;

public class JSONDocFieldWrapper implements Comparable<JSONDocFieldWrapper> {
	private Field field;
	private Integer order;

	public JSONDocFieldWrapper(Field field, Integer order) {
		this.field = field;
		this.order = order;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * This comparison is the same as the one in ApiObjectFieldDoc class 
	 */
	@Override
	public int compareTo(JSONDocFieldWrapper o) {
		if(this.getOrder().equals(o.getOrder())) {
			return this.getField().getName().compareTo(o.getField().getName());
		} else {
			return this.getOrder() - o.getOrder();
		}
	}
}