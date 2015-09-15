package org.jsondoc.core.util;

import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

public class JSONDocTemplateBuilderWithOrderTest {

	@ApiObject(name = "unordered")
	static class Unordered {

		@ApiObjectField(name = "xField")
		public String x;

		@ApiObjectField(name = "aField")
		public String a;

	}

	@ApiObject(name = "ordered")
	static class Ordered {

		@ApiObjectField(name = "xField", order = 1)
		public String x;

		@ApiObjectField(name = "aField", order = 2)
		public String a;

		@ApiObjectField(name = "bField", order = 2)
		public String b;
	}
	
	@Test
	public void thatTemplateIsMappedToStringCorrectly() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		Set<Class<?>> classes = Sets.<Class<?>>newHashSet(Unordered.class, Ordered.class);

		Map<String, Object> unorderedTemplate = JSONDocTemplateBuilder.build(Unordered.class, classes);
		Assert.assertEquals("{\"aField\":\"\",\"xField\":\"\"}", mapper.writeValueAsString(unorderedTemplate));

		Map<String, Object> orderedTemplate = JSONDocTemplateBuilder.build(Ordered.class, classes);
		Assert.assertEquals("{\"xField\":\"\",\"aField\":\"\",\"bField\":\"\"}", mapper.writeValueAsString(orderedTemplate));
	}

}
