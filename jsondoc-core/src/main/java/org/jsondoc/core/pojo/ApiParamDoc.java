package org.jsondoc.core.pojo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiParams;
import org.jsondoc.core.annotation.NullClass;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiParamDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private String type;
	private String required;
	private String[] allowedvalues;
	private String format;

	public ApiParamDoc(String name, String description, String type, String required, String[] allowedvalues, String format) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
		this.allowedvalues = allowedvalues;
		this.format = format;
	}

	public static List<ApiParamDoc> getApiParamDocs(Method method, ApiParamType paramType) {
		List<ApiParamDoc> docs = new ArrayList<ApiParamDoc>();
		Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parametersAnnotations.length; i++) {
			for (int j = 0; j < parametersAnnotations[i].length; j++) {
				if (parametersAnnotations[i][j] instanceof ApiParam) {
					ApiParam cur = (ApiParam) parametersAnnotations[i][j];
					String type;
					if( cur.clazz() != NullClass.class ) {
						type = getParamObjects(cur.clazz());
					} else {
						type = getParamObjects(method, i);
					}
					ApiParamDoc apiParamDoc = buildFromAnnotation(cur, type, paramType);
					if (apiParamDoc != null) {
						docs.add(apiParamDoc);
					}
				}
			}
		}
		ApiParams params = method.getAnnotation(ApiParams.class);
		if (params != null) {
			ApiParam[] apiParams = params.apiparams();
			for (ApiParam cur : apiParams) {
				if( cur.clazz() == NullClass.class ) {
					continue;
				}
				ApiParamDoc apiParamDoc = buildFromAnnotation(cur, getParamObjects(cur.clazz()), paramType);
				if (apiParamDoc != null) {
					docs.add(apiParamDoc);
				}
			}
		}
		ApiParam standaloneParam = method.getAnnotation(ApiParam.class);
		if (standaloneParam != null && standaloneParam.clazz() != NullClass.class) {
			ApiParamDoc apiParamDoc = buildFromAnnotation(standaloneParam, getParamObjects(standaloneParam.clazz()), paramType);
			if (apiParamDoc != null) {
				docs.add(apiParamDoc);
			}
		}

		return docs;
	}

	private static String getParamObjects(Method method, Integer index) {
		Class<?> parameter = method.getParameterTypes()[index];
		Type generic = method.getGenericParameterTypes()[index];
		if (Collection.class.isAssignableFrom(parameter)) {
			if (generic instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) generic;
				Type type = parameterizedType.getActualTypeArguments()[0];
				if (type instanceof WildcardType) {
					return JSONDocUtils.WILDCARD;
				}
				Class<?> clazz = (Class<?>) type;
				return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz);
			} else {
				return JSONDocUtils.UNDEFINED;
			}
		} else if (method.getReturnType().isArray()) {
			Class<?> classArr = parameter;
			return JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType());

		}
		return JSONDocUtils.getObjectNameFromAnnotatedClass(parameter);
	}

	private static String getParamObjects(Class<?> clazz) {
		if (clazz.isArray()) {
			return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz.getComponentType());
		}
		return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz);
	}

	public static ApiParamDoc buildFromAnnotation(ApiParam annotation, String type, ApiParamType paramType) {
		if (annotation.paramType().equals(paramType)) {
			return new ApiParamDoc(annotation.name(), annotation.description(), type, String.valueOf(annotation.required()), annotation.allowedvalues(), annotation.format());
		}
		return null;
	}

	public ApiParamDoc() {
		super();
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getRequired() {
		return required;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public String getFormat() {
		return format;
	}

}
