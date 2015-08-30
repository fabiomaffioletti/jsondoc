package org.jsondoc.core.pojo.global;

import java.util.UUID;

import org.jsondoc.core.pojo.ApiVerb;

public class ApiGlobalVerbDoc {
	public final String jsondocId = UUID.randomUUID().toString();

	private ApiVerb verb;

	private String description;

	public ApiVerb getVerb() {
		return verb;
	}

	public void setVerb(ApiVerb verb) {
		this.verb = verb;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
