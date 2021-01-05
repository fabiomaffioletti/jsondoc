package org.jsondoc.springmvc.scanner.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.getAnnotation;
import static org.jsondoc.springmvc.scanner.SpringBuilderUtils.isAnnotated;

public class SpringQueryParamBuilder {

	/**
	 * From Spring's documentation: Supported at the type level as well as at
	 * the method level! When used at the type level, all method-level mappings
	 * inherit this parameter restriction
	 * 
	 * @param method
	 * @return
	 */
	public static Set<ApiParamDoc> buildQueryParams(Method method) {
		Set<ApiParamDoc> apiParamDocs = new LinkedHashSet<ApiParamDoc>();
		Class<?> controller = method.getDeclaringClass();

		if (isAnnotated(controller, RequestMapping.class)) {
			RequestMapping requestMapping = getAnnotation(controller, RequestMapping.class);
			addRequestMappingParamDoc(apiParamDocs, requestMapping);
		}

		if (isAnnotated(method, RequestMapping.class)) {
			RequestMapping requestMapping = getAnnotation(method, RequestMapping.class);
			addRequestMappingParamDoc(apiParamDocs, requestMapping);
		}

		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			RequestParam requestParam = null;
			ModelAttribute modelAttribute = null;
			ApiQueryParam apiQueryParam = null;
			ApiParamDoc apiParamDoc = null;
			
			for (Annotation annotation : parametersAnnotations[i]) {
				if (annotation instanceof RequestParam) {
					requestParam = (RequestParam) annotation;
				}
				if(annotation instanceof ModelAttribute) {
					modelAttribute = (ModelAttribute) annotation;
				}
				if (annotation instanceof ApiQueryParam) {
					apiQueryParam = (ApiQueryParam) annotation;
				}
				
				if (requestParam != null) {
					apiParamDoc = new ApiParamDoc(nameOrValue(requestParam), "", JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i]), String.valueOf(requestParam.required()), new String[] {}, null, requestParam.defaultValue().equals(ValueConstants.DEFAULT_NONE) ? "" : requestParam.defaultValue());
					mergeApiQueryParamDoc(apiQueryParam, apiParamDoc);
				}
				if(modelAttribute != null) {
					apiParamDoc = new ApiParamDoc(modelAttribute.value(), "", JSONDocTypeBuilder.build(new JSONDocType(), method.getParameterTypes()[i], method.getGenericParameterTypes()[i]), "false", new String[] {}, null, null);
					mergeApiQueryParamDoc(apiQueryParam, apiParamDoc);
				}
				
			}
			
			if(apiParamDoc != null) {
				apiParamDocs.add(apiParamDoc);
			}
		}
		
		return apiParamDocs;
	}

	private static String nameOrValue(RequestParam requestParam) {
		try {
			return requestParam.value().isEmpty() ? requestParam.name() : requestParam.value();
		} catch (NoSuchMethodError nme) {
			// value is empty, name is not defined (introduced in 4.2, with spring3 this results in NoSuchMethodError)
		}
		return "";
	}

	/**
	 * Checks the request mapping annotation value and adds the resulting @ApiParamDoc to the documentation
	 * @param apiParamDocs
	 * @param requestMapping
	 */
	private static void addRequestMappingParamDoc(Set<ApiParamDoc> apiParamDocs, RequestMapping requestMapping) {
		if (requestMapping.params().length > 0) {
            for (String param : requestMapping.params()) {
                String[] splitParam = param.split("=");
                if (splitParam.length > 1) {
                    apiParamDocs.add(new ApiParamDoc(splitParam[0], "", JSONDocTypeBuilder.build(new JSONDocType(), String.class, null), "true", new String[] { splitParam[1] }, null, null));
                } else {
                    apiParamDocs.add(new ApiParamDoc(param, "", JSONDocTypeBuilder.build(new JSONDocType(), String.class, null), "true", new String[] {}, null, null));
                }
            }
        }
	}

	/**
	 * Available properties that can be overridden: name, description, required,
	 * allowedvalues, format, defaultvalue. Name is overridden only if it's empty
	 * in the apiParamDoc argument. Description, format and allowedvalues are
	 * copied in any case Default value and required are not overridden: in any
	 * case they are coming from the default values of @RequestParam
	 * 
	 * @param apiQueryParam
	 * @param apiParamDoc
	 */
	private static void mergeApiQueryParamDoc(ApiQueryParam apiQueryParam, ApiParamDoc apiParamDoc) {
		if (apiQueryParam != null) {
			if (apiParamDoc.getName().trim().isEmpty()) {
				apiParamDoc.setName(apiQueryParam.name());
			}
			apiParamDoc.setDescription(apiQueryParam.description());
			apiParamDoc.setAllowedvalues(apiQueryParam.allowedvalues());
			apiParamDoc.setFormat(apiQueryParam.format());
		}
	}

}
