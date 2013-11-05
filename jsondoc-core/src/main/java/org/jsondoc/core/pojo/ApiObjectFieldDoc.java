package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiObjectFieldDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String type;
	private String multiple;
	private String description;
	private String format;
	private String[] allowedvalues;
	private String mapKeyObject;
	private String mapValueObject;
	private String map;

	public static ApiObjectFieldDoc buildFromAnnotation(ApiObjectField annotation, Field field) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		apiPojoFieldDoc.setName(field.getName());
		apiPojoFieldDoc.setDescription(annotation.description());
		String[] typeChecks = getFieldObject(field);
		apiPojoFieldDoc.setType(typeChecks[0]);
		apiPojoFieldDoc.setMultiple(String.valueOf(JSONDocUtils.isMultiple(field.getType())));
		apiPojoFieldDoc.setFormat(annotation.format());
		apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		apiPojoFieldDoc.setMapKeyObject(typeChecks[1]);
		apiPojoFieldDoc.setMapValueObject(typeChecks[2]);
		apiPojoFieldDoc.setMap(typeChecks[3]);
		return apiPojoFieldDoc;
	}

	public static String[] getFieldObject(Field field) {
		if (Map.class.isAssignableFrom(field.getType())) {
			Class<?> mapKeyClazz = null;
			Class<?> mapValueClazz = null;

			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
				Type mapKeyType = parameterizedType.getActualTypeArguments()[0];
				Type mapValueType = parameterizedType.getActualTypeArguments()[1];
				mapKeyClazz = (Class<?>) mapKeyType;
				mapValueClazz = (Class<?>) mapValueType;
			}
			return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(field.getType()), (mapKeyClazz != null) ? mapKeyClazz.getSimpleName().toLowerCase() : null, (mapValueClazz != null) ? mapValueClazz.getSimpleName().toLowerCase() : null, "map" };

		} else if (Collection.class.isAssignableFrom(field.getType())) {
			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
				Type type = parameterizedType.getActualTypeArguments()[0];
				if(type instanceof WildcardType) {
					return new String[] { JSONDocUtils.WILDCARD, null, null, null };
				}
				Class<?> clazz = (Class<?>) type;
				return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(clazz), null, null, null };
			} else {
				return new String[] { JSONDocUtils.UNDEFINED, null, null, null };
			}

		} else if (field.getType().isArray()) {
			Class<?> classArr = field.getType();
			return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType()), null, null, null };

		}
		return new String[] { JSONDocUtils.getObjectNameFromAnnotatedClass(field.getType()), null, null, null };
	}

	public String getMapKeyObject() {
		return mapKeyObject;
	}

	public void setMapKeyObject(String mapKeyObject) {
		this.mapKeyObject = mapKeyObject;
	}

	public String getMapValueObject() {
		return mapValueObject;
	}

	public void setMapValueObject(String mapValueObject) {
		this.mapValueObject = mapValueObject;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String[] getAllowedvalues() {
		return allowedvalues;
	}

	public void setAllowedvalues(String[] allowedvalues) {
		this.allowedvalues = allowedvalues;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ApiObjectFieldDoc() {
		super();
	}

}
