package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocHibernateValidatorProcessor;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.jsondoc.core.util.JSONDocUtils;

public class JSONDocApiObjectFieldDocBuilder {

  public static ApiObjectFieldDoc build(ApiObjectField annotation, Field field) {
    ApiObjectFieldDoc doc = build(annotation, field.getName(), field.getType(), field.getGenericType());
    JSONDocHibernateValidatorProcessor.processHibernateValidatorAnnotations(field, doc);
    return doc;
  }
  
  public static ApiObjectFieldDoc build(ApiObjectField annotation, Method method) {
    ApiObjectFieldDoc doc = build(annotation, JSONDocUtils.getPropertyName(method), method.getReturnType(), method.getGenericReturnType());
    JSONDocHibernateValidatorProcessor.processHibernateValidatorAnnotations(method, doc);
    return doc;
  }

  private static ApiObjectFieldDoc build(ApiObjectField annotation, String name, Class<?> type, Type genericType) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		if (!annotation.name().trim().isEmpty()) {
			apiPojoFieldDoc.setName(annotation.name());
		} else {
			apiPojoFieldDoc.setName(name);
		}
		apiPojoFieldDoc.setDescription(annotation.description());
		apiPojoFieldDoc.setJsondocType(JSONDocTypeBuilder.build(new JSONDocType(), type, genericType));
		// if allowedvalues property is populated on an enum field, then the enum values are overridden with the allowedvalues ones
		if (type.isEnum() && annotation.allowedvalues().length == 0) {
			apiPojoFieldDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(type.getEnumConstants()));
		} else {
			apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		}
		apiPojoFieldDoc.setRequired(String.valueOf(annotation.required()));
		apiPojoFieldDoc.setOrder(annotation.order());
		
		if(!annotation.format().isEmpty()) {
			apiPojoFieldDoc.addFormat(annotation.format());
		}
		
		return apiPojoFieldDoc;
	}
	
}
