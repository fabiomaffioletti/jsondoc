package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.util.JSONDocType;

public class ApiParamDoc extends AbstractDoc implements Comparable<ApiParamDoc> {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private String name;
	private String description;
	private String required;
	private String[] allowedvalues;
	private String format;
	private String defaultvalue;

	public ApiParamDoc(String name, String description, JSONDocType jsondocType, String required, String[] allowedvalues, String format, String defaultvalue) {
		super();
		this.name = name;
		this.description = description;
		this.jsondocType = jsondocType;
		this.required = required;
		this.allowedvalues = allowedvalues;
		this.format = format;
		this.defaultvalue = defaultvalue;
	}

	public static ApiParamDoc buildFromAnnotation(ApiPathParam annotation, JSONDocType jsondocType, ApiParamType paramType) {
		return new ApiParamDoc(annotation.name(), annotation.description(), jsondocType, "true", annotation.allowedvalues(), annotation.format(), null);
	}

	public static ApiParamDoc buildFromAnnotation(ApiQueryParam annotation, JSONDocType jsondocType, ApiParamType paramType) {
		return new ApiParamDoc(annotation.name(), annotation.description(), jsondocType, String.valueOf(annotation.required()), annotation.allowedvalues(), annotation.format(), annotation.defaultvalue());
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRequired() {
		return required;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiParamDoc other = (ApiParamDoc) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(ApiParamDoc o) {
		return this.name.compareTo(o.getName());
	}

}
