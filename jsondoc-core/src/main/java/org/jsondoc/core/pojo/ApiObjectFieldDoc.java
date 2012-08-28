package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiObjectFieldDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String type;
	private boolean multiple;
	private String description;
	private String format;
	private String[] allowedvalues;

	public static ApiObjectFieldDoc buildFromAnnotation(ApiObjectField annotation, Field field) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		apiPojoFieldDoc.setName(field.getName());
		apiPojoFieldDoc.setDescription(annotation.description());
		apiPojoFieldDoc.setType(getFieldObject(field));
		apiPojoFieldDoc.setMultiple(JSONDocUtils.isMultiple(field.getType()));
		apiPojoFieldDoc.setFormat(annotation.format());
		apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		return apiPojoFieldDoc;
	}
	
	public static String getFieldObject(Field field) {
		if(Collection.class.isAssignableFrom(field.getType())) {
			if(field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
				Type type = parameterizedType.getActualTypeArguments()[0];
				Class<?> clazz = (Class<?>) type;
				return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz);
			} else {
				return JSONDocUtils.UNDEFINED;
			}
		} else if(field.getType().isArray()) {
			Class<?> classArr = field.getType();
			return JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType());
			
		}
		return JSONDocUtils.getObjectNameFromAnnotatedClass(field.getType());
	}
	
	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ApiObjectFieldDoc() {
		super();
	}

}
