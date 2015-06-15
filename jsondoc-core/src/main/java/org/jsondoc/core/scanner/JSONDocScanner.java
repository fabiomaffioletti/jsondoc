package org.jsondoc.core.scanner;

import org.jsondoc.core.pojo.*;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;

import java.util.List;
import java.util.Set;

public interface JSONDocScanner {
	
	JSONDoc getJSONDoc(String version, String basePath, List<String> packages, boolean playgroundEnabled, MethodDisplay methodDisplay);

	Set<ApiDoc> getApiDocs(Set<Class<?>> classes, MethodDisplay displayMethodAs);
	
	Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes);

	Set<ApiFlowDoc> getApiFlowDocs(Set<Class<?>> classes, List<ApiMethodDoc> apiMethodDocs);
	
}
