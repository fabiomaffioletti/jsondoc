package org.jsondoc.core.scanner;

import java.util.List;
import java.util.Set;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.pojo.flow.ApiFlowDoc;
import org.jsondoc.core.pojo.global.ApiGlobalDoc;

public interface JSONDocScanner {
	
	JSONDoc getJSONDoc(String version, String basePath, List<String> packages, boolean playgroundEnabled, MethodDisplay methodDisplay);

	Set<ApiDoc> getApiDocs(Set<Class<?>> classes, MethodDisplay displayMethodAs);
	
	Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes);

	Set<ApiFlowDoc> getApiFlowDocs(Set<Class<?>> classes, List<ApiMethodDoc> apiMethodDocs);

	ApiGlobalDoc getApiGlobalDoc(Set<Class<?>> global, Set<Class<?>> changelogs, Set<Class<?>> migrations);
	
}
