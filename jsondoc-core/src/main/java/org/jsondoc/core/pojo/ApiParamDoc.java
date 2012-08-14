package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiParams;

public class ApiParamDoc {
	private String name;
	private String description;
	private String type;
	private boolean required;
	private String[] allowedvalues;

	public ApiParamDoc(String name, String description, String type,
			boolean required, String[] allowedvalues) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
		this.allowedvalues = allowedvalues;
	}

	public static List<ApiParamDoc> buildFromAnnotation(ApiParams annotation) {
		List<ApiParamDoc> docs = new ArrayList<ApiParamDoc>();
		for (ApiParam apiParam : annotation.urlparameters()) {
			docs.add(new ApiParamDoc(apiParam.name(), apiParam.description(),
					apiParam.type(), apiParam.required(), apiParam
							.allowedvalues()));
		}
		return docs;
	}

	public ApiParamDoc() {
		super();
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRequired() {
		return required;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

}
