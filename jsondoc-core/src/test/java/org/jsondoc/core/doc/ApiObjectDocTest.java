package org.jsondoc.core.doc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Assert;
import org.junit.Test;

public class ApiObjectDocTest {
	
	@ApiObject(name="test-object")
	@ApiVersion(since = "1.0", until = "2.12")
	private class TestObject {
		
		@ApiObjectField(description="the test name", required = true)
		private String name;
		
		@ApiObjectField(description="the test age", required = false)
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

		@ApiObjectField(description="a Long array to test https://github.com/fabiomaffioletti/jsondoc/issues/27")
		private Long[] LongArray;

		@ApiObjectField(description="a long array to test https://github.com/fabiomaffioletti/jsondoc/issues/27")
		private long[] longArray;
		
		@ApiObjectField(name = "foo_bar", description="a property to test https://github.com/fabiomaffioletti/jsondoc/pull/31", required = true)
		private String fooBar;
		
		@ApiObjectField(name = "version", description="a property to test version since and until", required = true)
		@ApiVersion(since = "1.0", until = "2.12")
		private String version;
	}
	
	@Test
	public void testApiObjectDoc() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestObject.class);
		ApiObjectDoc childDoc = JSONDocUtils.getApiObjectDocs(classes).iterator().next(); 
		Assert.assertEquals("test-object", childDoc.getName());
		Assert.assertEquals(11, childDoc.getFields().size());
		Assert.assertEquals("1.0", childDoc.getApiVersion().getSince());
		Assert.assertEquals("2.12", childDoc.getApiVersion().getUntil());
		
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
				Assert.assertEquals("name", fieldDoc.getName());
				Assert.assertEquals("false", fieldDoc.getMultiple());
				Assert.assertEquals("true", fieldDoc.getRequired());
			}
			
			if(fieldDoc.getName().equals("age")) {
				Assert.assertEquals("integer", fieldDoc.getType());
				Assert.assertEquals("age", fieldDoc.getName());
				Assert.assertEquals("false", fieldDoc.getMultiple());
				Assert.assertEquals("false", fieldDoc.getRequired());
			}
			
			if(fieldDoc.getName().equals("avg")) {
				Assert.assertEquals("long", fieldDoc.getType());
				Assert.assertEquals("avg", fieldDoc.getName());
				Assert.assertEquals("false", fieldDoc.getMultiple());
				Assert.assertEquals("false", fieldDoc.getRequired());
			}
			
			if(fieldDoc.getName().equals("map")) {
				Assert.assertEquals("map", fieldDoc.getType());
				Assert.assertEquals("string", fieldDoc.getMapKeyObject());
				Assert.assertEquals("integer", fieldDoc.getMapValueObject());
				Assert.assertEquals("false", fieldDoc.getMultiple());
			}
			
			if(fieldDoc.getName().equals("LongArray")) {
				Assert.assertEquals("long", fieldDoc.getType());
				Assert.assertEquals("LongArray", fieldDoc.getName());
				Assert.assertEquals("true", fieldDoc.getMultiple());
				Assert.assertEquals("false", fieldDoc.getRequired());
			}

			if(fieldDoc.getName().equals("longArray")) {
				Assert.assertEquals("long", fieldDoc.getType());
				Assert.assertEquals("longArray", fieldDoc.getName());
				Assert.assertEquals("true", fieldDoc.getMultiple());
				Assert.assertEquals("false", fieldDoc.getRequired());
			}
			
			if(fieldDoc.getName().equals("fooBar")) {
				Assert.assertEquals("string", fieldDoc.getType());
				Assert.assertEquals("foo_bar", fieldDoc.getName());
				Assert.assertEquals("false", fieldDoc.getMultiple());
				Assert.assertEquals("false", fieldDoc.getRequired());
			}
			
			if(fieldDoc.getName().equals("version")) {
				Assert.assertEquals("string", fieldDoc.getType());
				Assert.assertEquals("1.0", fieldDoc.getApiVersion().getSince());
				Assert.assertEquals("2.12", fieldDoc.getApiVersion().getUntil());
			}
			
		}
	}

}
