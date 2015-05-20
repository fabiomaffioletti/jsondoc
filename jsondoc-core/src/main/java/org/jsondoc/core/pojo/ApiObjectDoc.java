package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocTemplateBuilder;

public class ApiObjectDoc extends AbstractDoc implements Comparable<ApiObjectDoc> {
	public final String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private List<ApiObjectFieldDoc> fields;
	private ApiVersionDoc supportedversions;
	private String[] allowedvalues;
	private String group;
	private Map<String, Object> jsondocTemplate;

	public ApiObjectDoc() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public static ApiObjectDoc buildFromAnnotation(ApiObject annotation, Class clazz) {
		ApiObjectDoc apiObjectDoc = new ApiObjectDoc();

		apiObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(new HashMap<String, Object>(), clazz));

		List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(ApiObjectField.class) != null) {
				ApiObjectFieldDoc fieldDoc = ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field);
				fieldDoc.setSupportedversions(ApiVersionDoc.build(field));

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
			apiObjectDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(clazz.getEnumConstants()));
		}

		if (annotation.name().trim().isEmpty()) {
			apiObjectDoc.setName(clazz.getSimpleName().toLowerCase());
		} else {
			apiObjectDoc.setName(annotation.name());
		}

		apiObjectDoc.setDescription(annotation.description());
		apiObjectDoc.setFields(fieldDocs);
		apiObjectDoc.setGroup(annotation.group());

		return apiObjectDoc;
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

	public Map<String, Object> getJsondocTemplate() {
		return jsondocTemplate;
	}

	public void setJsondocTemplate(Map<String, Object> jsondocTemplate) {
		this.jsondocTemplate = jsondocTemplate;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

}
