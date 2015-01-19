package org.jsondoc.core.util;

import java.util.List;
import java.util.Set;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiFlowDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;

public interface JSONDocScanner {
	
	JSONDoc getJSONDoc(String version, String basePath, List<String> packages);

	Set<ApiDoc> getApiDocs(Set<Class<?>> classes);
	
	Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes);

	Set<ApiFlowDoc> getApiFlowDocs(Set<Class<?>> classes, List<ApiMethodDoc> apiMethodDocs);
	
}
