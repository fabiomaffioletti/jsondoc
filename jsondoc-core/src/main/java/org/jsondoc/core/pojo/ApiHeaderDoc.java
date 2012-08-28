package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;

public class ApiHeaderDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;

	public ApiHeaderDoc(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public static List<ApiHeaderDoc> buildFromAnnotation(ApiHeaders annotation) {
		List<ApiHeaderDoc> docs = new ArrayList<ApiHeaderDoc>();
		for (ApiHeader apiHeader : annotation.headers()) {
			docs.add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description()));
		}
		return docs;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
