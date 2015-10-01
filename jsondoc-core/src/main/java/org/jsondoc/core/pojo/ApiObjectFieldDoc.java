package org.jsondoc.core.pojo;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.jsondoc.core.util.JSONDocType;

import com.google.common.base.Joiner;

public class ApiObjectFieldDoc extends AbstractDoc implements Comparable<ApiObjectFieldDoc> {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private String name;
	private String description;
	private Set<String> format;
	private String[] allowedvalues;
	private String required;
	private ApiVersionDoc supportedversions;
	private Integer order;

	public ApiObjectFieldDoc() {
		this.format = new LinkedHashSet<String>();
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public Set<String> getFormat() {
		return format;
	}
	
	public String getDisplayedFormat() {
		return Joiner.on(", ").join(format);
	}

	public void setFormat(Set<String> format) {
		this.format = format;
	}
	
	public void addFormat(String format) {
		this.format.add(format);
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

	public void setOrder(int order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
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
		ApiObjectFieldDoc other = (ApiObjectFieldDoc) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * This comparison is the same as the one in ApiObjectFieldDoc class 
	 */
	@Override
	public int compareTo(ApiObjectFieldDoc o) {
		if(this.getOrder().equals(o.getOrder())) {
			return this.getName().compareTo(o.getName());
		} else {
			return this.getOrder() - o.getOrder();
		}
	}

}