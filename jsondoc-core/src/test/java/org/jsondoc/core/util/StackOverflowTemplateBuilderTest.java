package org.jsondoc.core.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.util.pojo.StackOverflowTemplateObjectOne;
import org.jsondoc.core.util.pojo.StackOverflowTemplateObjectTwo;
import org.jsondoc.core.util.pojo.StackOverflowTemplateSelf;
import org.jsondoc.core.util.pojo.NotAnnotatedStackOverflowObjectOne;
import org.jsondoc.core.util.pojo.NotAnnotatedStackOverflowObjectTwo;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

public class StackOverflowTemplateBuilderTest {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testTemplate() throws JsonGenerationException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Set<Class<?>> classes = Sets.<Class<?>>newHashSet(StackOverflowTemplateSelf.class, StackOverflowTemplateObjectOne.class, StackOverflowTemplateObjectTwo.class);
		
		StackOverflowTemplateSelf objectSelf = new StackOverflowTemplateSelf();
		Map<String, Object> template = JSONDocTemplateBuilder.build(objectSelf.getClass(), classes);
		System.out.println(mapper.writeValueAsString(template));
		
		StackOverflowTemplateObjectOne objectOne = new StackOverflowTemplateObjectOne();
		template = JSONDocTemplateBuilder.build(objectOne.getClass(), classes);
		System.out.println(mapper.writeValueAsString(template));
		
		StackOverflowTemplateObjectTwo objectTwo = new StackOverflowTemplateObjectTwo();
		template = JSONDocTemplateBuilder.build(objectTwo.getClass(), classes);
		System.out.println(mapper.writeValueAsString(template));
	}
	
	@Test
	public void typeOneTwo() throws JsonGenerationException, JsonMappingException, IOException {
		Set<Class<?>> classes = Sets.<Class<?>>newHashSet(NotAnnotatedStackOverflowObjectOne.class, NotAnnotatedStackOverflowObjectTwo.class);
		
		NotAnnotatedStackOverflowObjectOne typeOne = new NotAnnotatedStackOverflowObjectOne();
		Map<String, Object> template = JSONDocTemplateBuilder.build(typeOne.getClass(), classes);
		System.out.println(mapper.writeValueAsString(template));
	}

}
