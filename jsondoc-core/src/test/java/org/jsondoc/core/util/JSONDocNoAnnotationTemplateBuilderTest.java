package org.jsondoc.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.util.pojo.NoAnnotationPojo;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

public class JSONDocNoAnnotationTemplateBuilderTest {

	@Test
	public void testTemplate() throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		ObjectMapper mapper = new ObjectMapper();
		Set<Class<?>> classes = Sets.<Class<?>>newHashSet(NoAnnotationPojo.class);
		
		Map<String, Object> template = JSONDocTemplateBuilder.build(NoAnnotationPojo.class, classes);
		System.out.println(mapper.writeValueAsString(template));
	}

}
