package org.jsondoc.core.util;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthBasic;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiAuthDoc;
import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class JSONDocUtils {
	public static final String UNDEFINED = "undefined";
	public static final String WILDCARD = "wildcard";
	public static final String ANONYMOUS = "anonymous";
	private static Reflections reflections = null;
	
	private static Logger log = Logger.getLogger(JSONDocUtils.class);
	
	/**
	 * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiObjectDoc</code> objects
	 * @return An <code>ApiDoc</code> object
	 */
	public static JSONDoc getApiDoc(String version, String basePath, List<String> packages) {
		return getApiDoc(version, basePath, packages, Thread.currentThread().getContextClassLoader());
	}
	
	public static JSONDoc getApiDoc(String version, String basePath, List<String> packages, ClassLoader cl) {
		Set<URL> urls = new HashSet<URL>();
		FilterBuilder filter = new FilterBuilder();
		
		if (log.isDebugEnabled()) {
			log.debug("Found " + packages.size() + " package(s) to scan...");
		}
		for (String pkg : packages) {
			if (log.isDebugEnabled()) {
				log.debug("Adding package to JSONDoc recursive scan: " + pkg);
			}
			urls.addAll(ClasspathHelper.forPackage(pkg, cl));
			filter.includePackage(pkg);
		}

		reflections = new Reflections(new ConfigurationBuilder()
			.filterInputsBy(filter)
			.setUrls(urls)
			.addClassLoader(cl)
			);
		
		JSONDoc apiDoc = new JSONDoc(version, basePath);
		apiDoc.setApis(getApiDocs(reflections.getTypesAnnotatedWith(Api.class)));
		apiDoc.setObjects(getApiObjectDocs(reflections.getTypesAnnotatedWith(ApiObject.class)));
		return apiDoc;
	}
	
	public static Set<ApiDoc> getApiDocs(Set<Class<?>> classes) {
		Set<ApiDoc> apiDocs = new TreeSet<ApiDoc>();
		for (Class<?> controller : classes) {
			if (log.isDebugEnabled()) {
				log.debug("Getting JSONDoc for class: " + controller.getName());
			}
			ApiDoc apiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
			if(controller.isAnnotationPresent(ApiVersion.class)) {
				apiDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(controller.getAnnotation(ApiVersion.class)));
			}
			
			apiDoc.setAuth(getApiAuthDocForController(controller));
			apiDoc.setMethods(getApiMethodDocs(controller));
			apiDocs.add(apiDoc);
		}
		return apiDocs;
	}
	
	public static Set<ApiObjectDoc> getApiObjectDocs(Set<Class<?>> classes) {
		Set<ApiObjectDoc> pojoDocs = new TreeSet<ApiObjectDoc>();
		for (Class<?> pojo : classes) {
			if (log.isDebugEnabled()) {
				log.debug("Getting JSONDoc for class: " + pojo.getName());
			}
			ApiObject annotation = pojo.getAnnotation(ApiObject.class);
			ApiObjectDoc pojoDoc = ApiObjectDoc.buildFromAnnotation(annotation, pojo);
			if(pojo.isAnnotationPresent(ApiVersion.class)) {
				pojoDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(pojo.getAnnotation(ApiVersion.class)));
			}
			
			if(annotation.show()) {
				pojoDocs.add(pojoDoc);
			}
		}
		return pojoDocs;
	}
	
	private static List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
		List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
		Method[] methods = controller.getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(ApiMethod.class)) {
				ApiMethodDoc apiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class));
				
				if(method.isAnnotationPresent(ApiHeaders.class)) {
					apiMethodDoc.setHeaders(ApiHeaderDoc.buildFromAnnotation(method.getAnnotation(ApiHeaders.class)));
				}
				
				apiMethodDoc.setPathparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.PATH));
				
				apiMethodDoc.setQueryparameters(ApiParamDoc.getApiParamDocs(method, ApiParamType.QUERY));
				
				apiMethodDoc.setBodyobject(ApiBodyObjectDoc.buildFromAnnotation(method));
				
				if(method.isAnnotationPresent(ApiResponseObject.class)) {
					apiMethodDoc.setResponse(ApiResponseObjectDoc.buildFromAnnotation(method.getAnnotation(ApiResponseObject.class), method));
				}
				
				if(method.isAnnotationPresent(ApiErrors.class)) {
					apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
				}
				
				if(method.isAnnotationPresent(ApiVersion.class)) {
					apiMethodDoc.setSupportedversions(ApiVersionDoc.buildFromAnnotation(method.getAnnotation(ApiVersion.class)));
				}
				
				apiMethodDoc.setAuth(getApiAuthDocForMethod(method, method.getDeclaringClass()));
				
				apiMethodDocs.add(apiMethodDoc);
			}
			
		}
		return apiMethodDocs;
	}
	
	public static String getObjectNameFromAnnotatedClass(Class<?> clazz) {
		Class<?> annotatedClass = ReflectionUtils.forName(clazz.getName());
		if(annotatedClass.isAnnotationPresent(ApiObject.class)) {
			return annotatedClass.getAnnotation(ApiObject.class).name();
		}
		return clazz.getSimpleName().toLowerCase();
	}
	
	public static boolean isMultiple(Method method) {
		if(Collection.class.isAssignableFrom(method.getReturnType()) || method.getReturnType().isArray()) {
			return true;
		}
		return false;
	}
	
	public static boolean isMultiple(Class<?> clazz) {
		if(Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
			return true;
		}
		return false;
	}
	
	public static String[] enumConstantsToStringArray(Object[] enumConstants) {
		String[] sarr = new String[enumConstants.length];
		for (int i = 0; i < enumConstants.length; i++) {
			sarr[i] = String.valueOf(enumConstants[i]);
		}
		return sarr;
	}
	
	private static ApiAuthDoc getApiAuthDocForController(Class<?> clazz) {
		if(clazz.isAnnotationPresent(ApiAuthNone.class)) {
			return ApiAuthDoc.buildFromApiAuthNoneAnnotation(clazz.getAnnotation(ApiAuthNone.class));
		}
		
		if(clazz.isAnnotationPresent(ApiAuthBasic.class)) {
			return ApiAuthDoc.buildFromApiAuthBasicAnnotation(clazz.getAnnotation(ApiAuthBasic.class));
		}
		
		return null;
	}
	
	private static ApiAuthDoc getApiAuthDocForMethod(Method method, Class<?> clazz) {
		if(method.isAnnotationPresent(ApiAuthNone.class)) {
			return ApiAuthDoc.buildFromApiAuthNoneAnnotation(method.getAnnotation(ApiAuthNone.class));
		}
		
		if(method.isAnnotationPresent(ApiAuthBasic.class)) {
			return ApiAuthDoc.buildFromApiAuthBasicAnnotation(method.getAnnotation(ApiAuthBasic.class));
		}
		
		return getApiAuthDocForController(clazz);
	}
	
}
