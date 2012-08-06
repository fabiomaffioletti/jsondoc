package org.jsondoc.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPojo;
import org.jsondoc.core.annotation.ApiPojoField;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiPojoDoc;
import org.jsondoc.core.pojo.ApiPojoFieldDoc;
import org.jsondoc.core.pojo.ApiRequestBodyObjectDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiURLParamDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;


public class JSONDocUtils {
	private static Reflections reflections = null;

	/**
	 * Returns all the classes annotated with a given annotation
	 * @param annotation
	 * @return a <code>Set</code> of <code>Class<?></code> annotated with the given annotation
	 */
	public static Set<Class<?>> getClassesWithAnnotation(Class<? extends Annotation> annotation) {
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);
		return classes;
	}
	
	private static Set<Class<?>> getAnnotatedControllers() {
		return getClassesWithAnnotation(Api.class);
	}
	
	private static Set<Class<?>> getAnnotatedPojos() {
		return getClassesWithAnnotation(ApiPojo.class);
	}
	
	/**
	 * Returns the main <code>ApiDoc</code>, containing <code>ApiMethodDoc</code> and <code>ApiPojoDoc</code> objects
	 * @return An <code>ApiDoc</code> object
	 */
	public static JSONDoc getApiDoc(ServletContext servletContext, String version, String basePath) {
		reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forWebInfClasses(servletContext)));
		JSONDoc apiDoc = new JSONDoc(version, basePath);
		apiDoc.setApis(getApiControllerDocs());
		apiDoc.setObjects(getApiPojoDocs());
		return apiDoc;
	}
	
	public static List<ApiDoc> getApiControllerDocs() {
		List<ApiDoc> apiDocs = new ArrayList<ApiDoc>();
		Set	<Class<?>> classes = getAnnotatedControllers();
		for (Class<?> controller : classes) {
			if(controller.getAnnotation(Api.class) != null) {
				ApiDoc apiDoc = ApiDoc.buildFromAnnotation(controller.getAnnotation(Api.class));
				apiDoc.setMethods(getApiMethodDocs(controller));
				apiDocs.add(apiDoc);
			}
			
		}
		return apiDocs;
	}
	
	public static List<ApiPojoDoc> getApiPojoDocs() {
		List<ApiPojoDoc> pojoDocs = new ArrayList<ApiPojoDoc>();
		Set<Class<?>> classes = getAnnotatedPojos();
		for (Class<?> pojo : classes) {
			List<ApiPojoFieldDoc> apiPojoFieldDocs = new ArrayList<ApiPojoFieldDoc>();
			ApiPojoDoc pojoDoc = ApiPojoDoc.buildFromAnnotation(pojo.getAnnotation(ApiPojo.class));
			for(Field field : pojo.getDeclaredFields()) {
				if(field.getAnnotation(ApiPojoField.class) != null) {
					apiPojoFieldDocs.add(ApiPojoFieldDoc.buildFromAnnotation(field.getAnnotation(ApiPojoField.class)));
				}
			}
			pojoDoc.setFields(apiPojoFieldDocs);
			pojoDocs.add(pojoDoc);
		}
		return pojoDocs;
	}
	
	private static List<ApiMethodDoc> getApiMethodDocs(Class<?> controller) {
		List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
		Method[] methods = controller.getMethods();
		for (Method method : methods) {
			if(method.getAnnotation(ApiMethod.class) != null) {
				ApiMethodDoc apiMethodDoc = ApiMethodDoc.buildFromAnnotation(method.getAnnotation(ApiMethod.class));
				apiMethodDoc.setUrlparameters(ApiURLParamDoc.buildApiURLParamDocs(method));
				apiMethodDoc.setResponse(ApiResponseObjectDoc.buildFromAnnotation(method.getAnnotation(ApiResponseObject.class)));
				apiMethodDoc.setBodyparameter(ApiRequestBodyObjectDoc.buildApiRequestBodyObjectDoc(method));
				apiMethodDoc.setApierrors(ApiErrorDoc.buildFromAnnotation(method.getAnnotation(ApiErrors.class)));
				apiMethodDocs.add(apiMethodDoc);
			}
		}
		return apiMethodDocs;
	}
	
}
