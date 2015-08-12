package org.jsondoc.core.scanner.validator;

import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;

import com.google.common.collect.Sets;

public class JSONDocApiMethodDocValidator {
	
	/**
	 * This checks that some of the properties are correctly set to produce a meaningful documentation and a working playground. In case this does not happen
	 * an error string is added to the jsondocerrors list in ApiMethodDoc.
	 * It also checks that some properties are be set to produce a meaningful documentation. In case this does not happen
	 * an error string is added to the jsondocwarnings list in ApiMethodDoc.
	 * @param apiMethodDoc
	 * @return
	 */
	public static ApiMethodDoc validateApiMethodDoc(ApiMethodDoc apiMethodDoc, MethodDisplay displayMethodAs) {
		final String ERROR_MISSING_METHOD_PATH 				= "Missing documentation data: path";
		final String ERROR_MISSING_PATH_PARAM_NAME 			= "Missing documentation data: path parameter name";
		final String ERROR_MISSING_QUERY_PARAM_NAME 		= "Missing documentation data: query parameter name";
		final String ERROR_MISSING_HEADER_NAME		 		= "Missing documentation data: header name";
		final String WARN_MISSING_METHOD_PRODUCES 			= "Missing documentation data: produces";
		final String WARN_MISSING_METHOD_CONSUMES 			= "Missing documentation data: consumes";
		final String HINT_MISSING_PATH_PARAM_DESCRIPTION 	= "Add description to ApiPathParam";
		final String HINT_MISSING_QUERY_PARAM_DESCRIPTION 	= "Add description to ApiQueryParam";
		final String HINT_MISSING_METHOD_DESCRIPTION 		= "Add description to ApiMethod";
		final String HINT_MISSING_METHOD_RESPONSE_OBJECT 	= "Add annotation ApiResponseObject to document the returned object";
		final String HINT_MISSING_METHOD_SUMMARY 			= "Method display set to SUMMARY, but summary info has not been specified";
		final String MESSAGE_MISSING_METHOD_SUMMARY			= "Missing documentation data: summary";
		
		if(apiMethodDoc.getPath().isEmpty()) {
			apiMethodDoc.setPath(Sets.newHashSet(ERROR_MISSING_METHOD_PATH));
			apiMethodDoc.addJsondocerror(ERROR_MISSING_METHOD_PATH);
		}
		
		if(apiMethodDoc.getSummary().trim().isEmpty() && displayMethodAs.equals(MethodDisplay.SUMMARY)) {
			apiMethodDoc.setSummary(MESSAGE_MISSING_METHOD_SUMMARY);
			apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_SUMMARY);
		}
		
		for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
			if(apiParamDoc.getName().trim().isEmpty()) {
				apiMethodDoc.addJsondocerror(ERROR_MISSING_PATH_PARAM_NAME);
			}
			
			if(apiParamDoc.getDescription().trim().isEmpty()) {
				apiMethodDoc.addJsondochint(HINT_MISSING_PATH_PARAM_DESCRIPTION);
			}
		}
		
		for (ApiParamDoc apiParamDoc : apiMethodDoc.getQueryparameters()) {
			if(apiParamDoc.getName().trim().isEmpty()) {
				apiMethodDoc.addJsondocerror(ERROR_MISSING_QUERY_PARAM_NAME);
			}
			
			if(apiParamDoc.getDescription().trim().isEmpty()) {
				apiMethodDoc.addJsondochint(HINT_MISSING_QUERY_PARAM_DESCRIPTION);
			}
		}
		
		for (ApiHeaderDoc apiHeaderDoc : apiMethodDoc.getHeaders()) {
			if(apiHeaderDoc.getName().trim().isEmpty()) {
				apiMethodDoc.addJsondocerror(ERROR_MISSING_HEADER_NAME);
			}
		}
		
		if(apiMethodDoc.getProduces().isEmpty()) {
			apiMethodDoc.addJsondocwarning(WARN_MISSING_METHOD_PRODUCES);
		}
		
		if((apiMethodDoc.getVerb().contains(ApiVerb.POST) || apiMethodDoc.getVerb().contains(ApiVerb.PUT)) && apiMethodDoc.getConsumes().isEmpty()) {
			apiMethodDoc.addJsondocwarning(WARN_MISSING_METHOD_CONSUMES);
		}
		
		if(apiMethodDoc.getDescription().trim().isEmpty()) {
			apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_DESCRIPTION);
		}
		
		if(apiMethodDoc.getResponse() == null) {
			apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_RESPONSE_OBJECT);
		}
		
		return apiMethodDoc;
	}

}
