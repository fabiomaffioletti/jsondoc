package org.jsondoc.core.doc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Assert;
import org.junit.Test;

public class ApiObjectDocTest {

	@ApiObject(name="test-object")
	private class TestObject {

		@ApiObjectField(description="the test name")
		private String name;

		@ApiObjectField(description="the test age")
		private Integer age;

		@ApiObjectField(description="the test avg")
		private Long avg;

		@ApiObjectField(description="the test map")
		private Map<String, Integer> map;

		@SuppressWarnings("rawtypes")
		@ApiObjectField(description="an unparameterized list to test https://github.com/fabiomaffioletti/jsondoc/issues/5")
		private List unparameterizedList;

		@ApiObjectField(description="a parameterized list")
		private List<String> parameterizedList;

		@ApiObjectField(description="a wildcard parameterized list to test https://github.com/fabiomaffioletti/jsondoc/issues/5")
		private List<?> wildcardParameterized;

		@ApiObjectField(description="a parameterized list where the Type Param is generically typed")
		private List<Set<?>> parameterizedListOfGenericType;

		@ApiObjectField(description="an array parameterized list where the Type Param is an array")
		private List<String[]> parameterizedListOfArray;

	}

	@Test
	public void testApiObjectDoc() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestObject.class);
		ApiObjectDoc childDoc = JSONDocUtils.getApiObjectDocs(classes).iterator().next();
		Assert.assertEquals("test-object", childDoc.getName());
		Assert.assertEquals(9, childDoc.getFields().size());

		ApiObjectFieldDoc fieldDoc = getFieldDoc(childDoc.getFields(), "parameterizedListOfArray");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("string", fieldDoc.getType());
		Assert.assertEquals("true", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "parameterizedListOfGenericType");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("set", fieldDoc.getType());
		Assert.assertEquals("true", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "wildcardParameterized");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("wildcard", fieldDoc.getType());
		Assert.assertEquals("true", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "unparameterizedList");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("undefined", fieldDoc.getType());
		Assert.assertEquals("true", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "parameterizedList");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("string", fieldDoc.getType());
		Assert.assertEquals("true", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "name");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("string", fieldDoc.getType());
		Assert.assertEquals("false", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "age");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("integer", fieldDoc.getType());
		Assert.assertEquals("false", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "avg");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("long", fieldDoc.getType());
		Assert.assertEquals("false", fieldDoc.getMultiple());

		fieldDoc = getFieldDoc(childDoc.getFields(), "map");
		Assert.assertNotNull(fieldDoc);
		Assert.assertEquals("map", fieldDoc.getType());
		Assert.assertEquals("string", fieldDoc.getMapKeyObject());
		Assert.assertEquals("integer", fieldDoc.getMapValueObject());
		Assert.assertEquals("false", fieldDoc.getMultiple());
	}

	private ApiObjectFieldDoc getFieldDoc(List<ApiObjectFieldDoc> fieldDocs, String fieldName) {
		for (ApiObjectFieldDoc fieldDoc : fieldDocs) {
			if (fieldDoc.getName().equals(fieldName)) {
				return fieldDoc;
			}
		}
		return null;
	}

}
