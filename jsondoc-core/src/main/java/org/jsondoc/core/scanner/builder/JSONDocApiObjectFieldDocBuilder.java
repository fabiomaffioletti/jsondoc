package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Field;

import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocHibernateValidatorProcessor;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class JSONDocApiObjectFieldDocBuilder {

	public static ApiObjectFieldDoc build(ApiObjectField annotation, Field field) {
		ApiObjectFieldDoc apiPojoFieldDoc = new ApiObjectFieldDoc();
		if (!annotation.name().trim().isEmpty()) {
			apiPojoFieldDoc.setName(annotation.name());
		} else {
			apiPojoFieldDoc.setName(field.getName());
		}
		apiPojoFieldDoc.setDescription(annotation.description());
		apiPojoFieldDoc.setJsondocType(JSONDocTypeBuilder.build(new JSONDocType(), field.getType(), field.getGenericType()));
		// if allowedvalues property is populated on an enum field, then the enum values are overridden with the allowedvalues ones
		if (field.getType().isEnum() && annotation.allowedvalues().length == 0) {
			apiPojoFieldDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(field.getType().getEnumConstants()));
		} else {
			apiPojoFieldDoc.setAllowedvalues(annotation.allowedvalues());
		}
		apiPojoFieldDoc.setRequired(String.valueOf(annotation.required()));
		apiPojoFieldDoc.setOrder(annotation.order());
		
		if(!annotation.format().isEmpty()) {
			apiPojoFieldDoc.addFormat(annotation.format());
		}
		
		JSONDocHibernateValidatorProcessor.processHibernateValidatorAnnotations(field, apiPojoFieldDoc);
		
		return apiPojoFieldDoc;
	}
	
}
