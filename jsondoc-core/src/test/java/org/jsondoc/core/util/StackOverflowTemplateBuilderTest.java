package org.jsondoc.core.util;

import java.io.IOException;
import java.util.Map;

import org.jsondoc.core.util.pojo.StackOverflowTemplateObjectOne;
import org.jsondoc.core.util.pojo.StackOverflowTemplateObjectTwo;
import org.jsondoc.core.util.pojo.StackOverflowTemplateSelf;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StackOverflowTemplateBuilderTest {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testTemplate() throws JsonGenerationException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		StackOverflowTemplateSelf objectSelf = new StackOverflowTemplateSelf();
		Map<String, Object> template = JSONDocTemplateBuilder.build(objectSelf.getClass()).getMap();
		System.out.println(mapper.writeValueAsString(template));
		
		StackOverflowTemplateObjectOne objectOne = new StackOverflowTemplateObjectOne();
		template = JSONDocTemplateBuilder.build(objectOne.getClass()).getMap();
		System.out.println(mapper.writeValueAsString(template));
		
		StackOverflowTemplateObjectTwo objectTwo = new StackOverflowTemplateObjectTwo();
		template = JSONDocTemplateBuilder.build(objectTwo.getClass()).getMap();
		System.out.println(mapper.writeValueAsString(template));
	}

}
