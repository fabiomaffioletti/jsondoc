package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiObjectGetter;

public class ApiObjectDoc implements Comparable<ApiObjectDoc> {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private List<ApiObjectFieldDoc> fields;
	
	@SuppressWarnings("rawtypes")
	public static ApiObjectDoc buildFromAnnotation( ApiObject annotation, Class clazz )
	{
	    
		final List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
		for( Field field : clazz.getDeclaredFields() )
			if( field.getAnnotation(ApiObjectField.class) != null )
				fieldDocs.add(ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field) );

		for( Method getter : clazz.getDeclaredMethods() )
		    if( getter.getAnnotation(ApiObjectGetter.class) != null &&
		        getter.getName().startsWith("get") && getter.getParameterTypes().length == 0 )
		        fieldDocs.add( ApiObjectFieldDoc.buildFromAnnotation(getter.getAnnotation(ApiObjectGetter.class), getter) );

		Class<?> c = clazz.getSuperclass();
		if (c != null) {
			if (c.isAnnotationPresent(ApiObject.class)) {
				ApiObjectDoc objDoc = ApiObjectDoc.buildFromAnnotation(c.getAnnotation(ApiObject.class), c);
				fieldDocs.addAll(objDoc.getFields());
			}
		}

		return new ApiObjectDoc(annotation.name(), annotation.description(), fieldDocs);
	}

	public ApiObjectDoc(String name, String description, List<ApiObjectFieldDoc> fields) {
		super();
		this.name = name;
		this.description = description;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<ApiObjectFieldDoc> getFields() {
		return fields;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
