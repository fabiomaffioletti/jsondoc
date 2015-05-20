package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class ApiObjectFieldDoc extends AbstractDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private String name;
	private String description;
	private String format;
	private String[] allowedvalues;
	private String required;
	private ApiVersionDoc supportedversions;

	public static ApiObjectFieldDoc buildFromAnnotation(ApiObjectField annotation, Field field) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		if (!annotation.name().trim().isEmpty()) {
			apiPojoFieldDoc.setName(annotation.name());
		} else {
			apiPojoFieldDoc.setName(field.getName());
		}
		apiPojoFieldDoc.setDescription(annotation.description());
		apiPojoFieldDoc.setJsondocType(JSONDocTypeBuilder.build(new JSONDocType(), field.getType(), field.getGenericType()));
		apiPojoFieldDoc.setFormat(annotation.format());
		if (field.getType().isEnum()) {
			apiPojoFieldDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(field.getType().getEnumConstants()));
		} else {
			apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		}
		apiPojoFieldDoc.setRequired(String.valueOf(annotation.required()));
		return apiPojoFieldDoc;
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

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public ApiVersionDoc getSupportedversions() {
		return supportedversions;
	}

	public void setSupportedversions(ApiVersionDoc supportedversions) {
		this.supportedversions = supportedversions;
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

	public void setJsondocType(JSONDocType jsondocType) {
		this.jsondocType = jsondocType;
	}

	public ApiObjectFieldDoc() {
		super();
	}

}
