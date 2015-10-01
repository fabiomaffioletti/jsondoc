package org.jsondoc.springmvc.scanner.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.TreeSet;

import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocHibernateValidatorProcessor;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;

public class SpringObjectBuilder {

	public static ApiObjectDoc buildObject(Class<?> clazz) {
		ApiObjectDoc apiObjectDoc = new ApiObjectDoc();
		apiObjectDoc.setName(clazz.getSimpleName());
		
		Set<ApiObjectFieldDoc> fieldDocs = new TreeSet<ApiObjectFieldDoc>();
		
		for (Field field : clazz.getDeclaredFields()) {
			ApiObjectFieldDoc fieldDoc = new ApiObjectFieldDoc();
			fieldDoc.setName(field.getName());
			fieldDoc.setOrder(Integer.MAX_VALUE);
			fieldDoc.setRequired(DefaultJSONDocScanner.UNDEFINED.toUpperCase());
			fieldDoc.setJsondocType(JSONDocTypeBuilder.build(new JSONDocType(), field.getType(), field.getGenericType()));
			
			JSONDocHibernateValidatorProcessor.processHibernateValidatorAnnotations(field, fieldDoc);
			
			fieldDocs.add(fieldDoc);
		}
		
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null) {
			ApiObjectDoc parentObjectDoc = buildObject(superclass);
			fieldDocs.addAll(parentObjectDoc.getFields());
		}
		
		if (clazz.isEnum()) {
			apiObjectDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(clazz.getEnumConstants()));
		}
		
		apiObjectDoc.setFields(fieldDocs);
		if(Modifier.isAbstract(clazz.getModifiers())) {
			apiObjectDoc.setShow(false);
		}
		
		return apiObjectDoc;
	}

}
