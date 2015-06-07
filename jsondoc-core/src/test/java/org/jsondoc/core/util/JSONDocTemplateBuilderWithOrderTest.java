package org.jsondoc.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class JSONDocTemplateBuilderWithOrderTest {

	@Test
	public void thatTemplateIsMappedToStringCorrectly() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		Map<String, Object> unorderedTemplate = JSONDocTemplateBuilder.build(Unordered.class).getMap();
		Assert.assertEquals(
				"{\"aField\":\"\",\"xField\":\"\"}", mapper.writeValueAsString(unorderedTemplate)
		);

		Map<String, Object> orderedTemplate = JSONDocTemplateBuilder.build(Ordered.class).getMap();
		Assert.assertEquals(
				"{\"xField\":\"\",\"aField\":\"\",\"bField\":\"\"}",
				mapper.writeValueAsString(orderedTemplate)
		);
	}

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

}
