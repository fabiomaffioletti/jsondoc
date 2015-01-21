package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;

public class ApiHeaderDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private String[] allowedvalues;

	public ApiHeaderDoc(String name, String description, String[] allowedvalues) {
		super();
		this.name = name;
		this.description = description;
		this.allowedvalues = allowedvalues;
	}

	public static Set<ApiHeaderDoc> build(Method method) {
		Set<ApiHeaderDoc> docs = new LinkedHashSet<ApiHeaderDoc>();

		if (method.isAnnotationPresent(ApiHeaders.class)) {
			ApiHeaders annotation = method.getAnnotation(ApiHeaders.class);
			for (ApiHeader apiHeader : annotation.headers()) {
				docs.add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description(), apiHeader.allowedvalues()));
			}
		}

		return docs;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
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
		ApiHeaderDoc other = (ApiHeaderDoc) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
