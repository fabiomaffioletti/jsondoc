package org.jsondoc.core.pojo.global;

import java.util.UUID;

public class ApiGlobalResponseStatusCodeDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private String code;

	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
