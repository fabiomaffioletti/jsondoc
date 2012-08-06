package org.jsondoc.core.pojo;

import java.util.List;

import org.jsondoc.core.annotation.ApiPojo;


public class ApiPojoDoc {
	private String name;
	private List<ApiPojoFieldDoc> fields;
	
	public static ApiPojoDoc buildFromAnnotation(ApiPojo annotation) {
		return new ApiPojoDoc(annotation.name());
	}
	
	public ApiPojoDoc(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ApiPojoFieldDoc> getFields() {
		return fields;
	}

	public void setFields(List<ApiPojoFieldDoc> fields) {
		this.fields = fields;
	}

}
