package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.util.JSONDocType;

public class ApiBodyObjectDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private JSONDocType jsondocType;
	private JSONDocTemplate jsondocTemplate;

	public ApiBodyObjectDoc(JSONDocType jsondocType) {
		this.jsondocType = jsondocType;
	}

	public JSONDocType getJsondocType() {
		return jsondocType;
	}

	public JSONDocTemplate getJsondocTemplate() {
		return jsondocTemplate;
	}

	public void setJsondocTemplate(JSONDocTemplate jsondocTemplate) {
		this.jsondocTemplate = jsondocTemplate;
	}

	public void setJsondocType(JSONDocType jsondocType) {
		this.jsondocType = jsondocType;
	}

}
