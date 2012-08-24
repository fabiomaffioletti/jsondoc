package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.reflections.ReflectionUtils;

public class ApiObjectDoc implements Comparable<ApiObjectDoc> {
	private String name;
	private List<ApiObjectFieldDoc> fields;

	@SuppressWarnings("rawtypes")
	public static ApiObjectDoc buildFromAnnotation(ApiObject annotation, Class clazz) {
		List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
		for(Field field : clazz.getDeclaredFields()) {
			if(field.getAnnotation(ApiObjectField.class) != null) {
				fieldDocs.add(ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field.getName()));
			}
		}
		
		if(annotation.extendsclass() != null && !annotation.extendsclass().trim().equals("")) {
			Class<?> c = ReflectionUtils.forName(annotation.extendsclass());
			ApiObjectDoc objDoc = ApiObjectDoc.buildFromAnnotation(c.getAnnotation(ApiObject.class), c);
			fieldDocs.addAll(objDoc.getFields());
		}
		
		return new ApiObjectDoc(annotation.name(), fieldDocs);
	}

	public ApiObjectDoc(String name, List<ApiObjectFieldDoc> fields) {
		super();
		this.name = name;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public List<ApiObjectFieldDoc> getFields() {
		return fields;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
