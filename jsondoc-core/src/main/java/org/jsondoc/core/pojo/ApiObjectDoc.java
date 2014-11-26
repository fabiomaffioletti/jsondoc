package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiObjectDoc implements Comparable<ApiObjectDoc> {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private List<ApiObjectFieldDoc> fields;
	private ApiVersionDoc supportedversions;
	private String[] allowedvalues;
	private String group;

	@SuppressWarnings("rawtypes")
	public static ApiObjectDoc buildFromAnnotation(ApiObject annotation, Class clazz) {
		ApiObjectDoc apiObjectDoc = new ApiObjectDoc();

		List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(ApiObjectField.class) != null) {
				ApiObjectFieldDoc fieldDoc = ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field);

				if (field.isAnnotationPresent(ApiVersion.class)) {
					fieldDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(field.getAnnotation(ApiVersion.class)));
				}

				fieldDocs.add(fieldDoc);
			}
		}

		Class<?> c = clazz.getSuperclass();
		if (c != null) {
			if (c.isAnnotationPresent(ApiObject.class)) {
				ApiObjectDoc objDoc = ApiObjectDoc.buildFromAnnotation(c.getAnnotation(ApiObject.class), c);
				fieldDocs.addAll(objDoc.getFields());
			}
		}

		if (clazz.isEnum()) {
			apiObjectDoc.setAllowedvalues(JSONDocUtils.enumConstantsToStringArray(clazz.getEnumConstants()));
		}

		apiObjectDoc.setName(annotation.name());
		apiObjectDoc.setDescription(annotation.description());
		apiObjectDoc.setFields(fieldDocs);
		apiObjectDoc.setGroup(annotation.group());

		return apiObjectDoc;
	}

	public ApiObjectDoc() {
		super();
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

	public List<ApiObjectFieldDoc> getFields() {
		return fields;
	}

	public void setFields(List<ApiObjectFieldDoc> fields) {
		this.fields = fields;
	}

	public ApiVersionDoc getSupportedversions() {
		return supportedversions;
	}

	public void setSupportedversions(ApiVersionDoc supportedversions) {
		this.supportedversions = supportedversions;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
