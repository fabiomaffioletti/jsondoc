package org.jsondoc.core.util;

import java.lang.reflect.Type;

import org.jsondoc.core.annotation.ApiObjectField;

public class JSONDocFieldWrapper implements Comparable<JSONDocFieldWrapper> {
	private String name;
	private Class<?> type;
	private Type genericType;
	private ApiObjectField apiObjectFieldAnnotation;
	
  private Integer order;

	public JSONDocFieldWrapper(
	    String name, 
	    Class<?> type, 
	    Type genericType,
	    ApiObjectField apiObjectFieldAnnotation, 
	    Integer order) {
		
	  this.name = name;
		this.type = type;
		this.genericType = genericType;
		this.apiObjectFieldAnnotation = apiObjectFieldAnnotation;
		this.order = order;
	}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

  public Type getGenericType() {
    return genericType;
  }

  public void setGenericType(Type genericType) {
    this.genericType = genericType;
  }

  public ApiObjectField getApiObjectFieldAnnotation() {
    return apiObjectFieldAnnotation;
  }

  public void setApiObjectFieldAnnotation(ApiObjectField apiObjectFieldAnnotation) {
    this.apiObjectFieldAnnotation = apiObjectFieldAnnotation;
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
			return this.getName().compareTo(o.getName());
		} else {
			return this.getOrder() - o.getOrder();
		}
	}
}