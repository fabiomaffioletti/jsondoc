package org.jsondoc.core.scanner.builder;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.util.JSONDocTemplateBuilder;

public class JSONDocApiObjectDocBuilder {
	
	public static ApiObjectDoc build(Class<?> clazz) {
		ApiObject apiObject = clazz.getAnnotation(ApiObject.class);
		
		ApiObjectDoc apiObjectDoc = new ApiObjectDoc();

		Set<ApiObjectFieldDoc> fieldDocs = new TreeSet<ApiObjectFieldDoc>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(ApiObjectField.class) != null) {
				ApiObjectFieldDoc fieldDoc = ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field);
				fieldDoc.setSupportedversions(JSONDocApiVersionDocBuilder.build(field));

				fieldDocs.add(fieldDoc);
			}
		}

		Class<?> c = clazz.getSuperclass();
		if (c != null) {
			if (c.isAnnotationPresent(ApiObject.class)) {
				ApiObjectDoc objDoc = build(c);
				fieldDocs.addAll(objDoc.getFields());
			}
		}

		if (clazz.isEnum()) {
			apiObjectDoc.setAllowedvalues(DefaultJSONDocScanner.enumConstantsToStringArray(clazz.getEnumConstants()));
		}

		if (apiObject.name().trim().isEmpty()) {
			apiObjectDoc.setName(clazz.getSimpleName().toLowerCase());
		} else {
			apiObjectDoc.setName(apiObject.name());
		}

		apiObjectDoc.setDescription(apiObject.description());
		apiObjectDoc.setStage(apiObject.stage().getLabel());
		apiObjectDoc.setVisibility(apiObject.visibility().getLabel());
		apiObjectDoc.setFields(fieldDocs);
		apiObjectDoc.setGroup(apiObject.group());

		apiObjectDoc.setJsondocTemplate(JSONDocTemplateBuilder.build(clazz));
		return apiObjectDoc;
	}

}
