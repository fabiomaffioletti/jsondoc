package org.jsondoc.core.scanner;

import java.lang.reflect.Method;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;

public class DefaultJSONDocScanner extends AbstractJSONDocScanner {
	public static final String UNDEFINED = "undefined";
	public static final String ANONYMOUS = "anonymous";
	
	@Override
	public ApiDoc mergeApiDoc(Class<?> controller, ApiDoc apiDoc) {
		return apiDoc;
	}

	@Override
	public ApiMethodDoc mergeApiMethodDoc(Method method, Class<?> controller, ApiMethodDoc apiMethodDoc) {
		return apiMethodDoc;
	}

	@Override
	public ApiParamDoc mergeApiPathParamDoc(Method method, int paramIndex, ApiParamDoc apiParamDoc) {
		return apiParamDoc;
	}

	@Override
	public ApiParamDoc mergeApiQueryParamDoc(Method method, int paramIndex, ApiParamDoc apiParamDoc) {
		return apiParamDoc;
	}
	
}
