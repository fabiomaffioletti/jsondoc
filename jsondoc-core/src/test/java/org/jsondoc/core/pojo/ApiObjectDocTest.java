package org.jsondoc.core.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
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
		@ApiObjectField(description="an unparametrized list to test https://github.com/fabiomaffioletti/jsondoc/issues/5")
		private List unparametrizedList;
		
		@ApiObjectField(description="a parametrized list")
		private List<String> parametrizedList;
		
		@ApiObjectField(description="a wildcard parametrized list to test https://github.com/fabiomaffioletti/jsondoc/issues/5")
		private List<?> wildcardParametrized;
		
	}
	
	@Test
	public void testApiObjectDoc() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestObject.class);
		ApiObjectDoc childDoc = JSONDocUtils.getApiObjectDocs(classes).iterator().next(); 
		Assert.assertEquals("test-object", childDoc.getName());
		Assert.assertEquals(7, childDoc.getFields().size());
		
		for (ApiObjectFieldDoc fieldDoc : childDoc.getFields()) {
			if(fieldDoc.getName().equals("wildcardParametrized")) {
				Assert.assertEquals("wildcard", fieldDoc.getType());
				Assert.assertEquals("true", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("unparametrizedList")) {
				Assert.assertEquals("undefined", fieldDoc.getType());
				Assert.assertEquals("true", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("parametrizedList")) {
				Assert.assertEquals("string", fieldDoc.getType());
				Assert.assertEquals("true", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("name")) {
				Assert.assertEquals("string", fieldDoc.getType());
				Assert.assertEquals("false", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("age")) {
				Assert.assertEquals("integer", fieldDoc.getType());
				Assert.assertEquals("false", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("avg")) {
				Assert.assertEquals("long", fieldDoc.getType());
				Assert.assertEquals("false", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("map")) {
				Assert.assertEquals("map", fieldDoc.getType());
				Assert.assertEquals("string", fieldDoc.getMapKeyObject());
				Assert.assertEquals("integer", fieldDoc.getMapValueObject());
				Assert.assertEquals("false", fieldDoc.getMultiple());
			}
		}
	}

}
