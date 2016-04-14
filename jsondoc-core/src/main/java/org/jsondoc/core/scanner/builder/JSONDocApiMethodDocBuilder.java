package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.ApiVisibility;

public class JSONDocApiMethodDocBuilder {
	
	public static ApiMethodDoc build(Method method) {
		ApiMethod methodAnnotation = method.getAnnotation(ApiMethod.class);
		Api controllerAnnotation = method.getDeclaringClass().getAnnotation(Api.class);
		
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setId(methodAnnotation.id());
		apiMethodDoc.setPath(new LinkedHashSet<String>(Arrays.asList(methodAnnotation.path())));
		apiMethodDoc.setMethod(method.getName());
		apiMethodDoc.setSummary(methodAnnotation.summary());
		apiMethodDoc.setDescription(methodAnnotation.description());
		apiMethodDoc.setVerb(new LinkedHashSet<ApiVerb>(Arrays.asList(methodAnnotation.verb())));
		apiMethodDoc.setConsumes(new LinkedHashSet<String>(Arrays.asList(methodAnnotation.consumes())));
		apiMethodDoc.setProduces(new LinkedHashSet<String>(Arrays.asList(methodAnnotation.produces())));
		apiMethodDoc.setResponsestatuscode(methodAnnotation.responsestatuscode());
		
		if(methodAnnotation.visibility().equals(ApiVisibility.UNDEFINED)) {
			apiMethodDoc.setVisibility(controllerAnnotation.visibility());
		} else {
			apiMethodDoc.setVisibility(methodAnnotation.visibility());
		}
		
		if(methodAnnotation.stage().equals(ApiStage.UNDEFINED)) {
			apiMethodDoc.setStage(controllerAnnotation.stage());
		} else {
			apiMethodDoc.setStage(methodAnnotation.stage());
		}
		
		apiMethodDoc.setHeaders(JSONDocApiHeaderDocBuilder.build(method));
		apiMethodDoc.setPathparameters(JSONDocApiPathParameterDocBuilder.build(method));
		apiMethodDoc.setQueryparameters(JSONDocApiQueryParameterDocBuilder.build(method));
		apiMethodDoc.setBodyobject(JSONDocApiBodyObjectDocBuilder.build(method));
		apiMethodDoc.setResponse(JSONDocApiResponseDocBuilder.build(method));

		return apiMethodDoc;
	}
	
}
