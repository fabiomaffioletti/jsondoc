package org.jsondoc.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.hamcrest.CoreMatchers;
import org.jsondoc.core.pojo.JSONDocTemplate;
import org.jsondoc.core.util.pojo.ClassWithConstant;
import org.jsondoc.core.util.pojo.TemplateObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jsondoc.core.util.pojo.ClassWithConstant.THIS_IS_A_CONSTANT;

public class JSONDocTemplateBuilderTest {

	@Test
	public void testTemplate() throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		ObjectMapper mapper = new ObjectMapper();
		Set<Class<?>> classes = Sets.<Class<?>>newHashSet(TemplateObject.class);
		
		Map<String, Object> template = JSONDocTemplateBuilder.build(TemplateObject.class, classes);

		Assert.assertEquals(0, template.get("my_id"));
		Assert.assertEquals(0, template.get("idint"));
		Assert.assertEquals(0, template.get("idlong"));
		Assert.assertEquals("", template.get("name"));
		Assert.assertEquals("", template.get("gender"));
		Assert.assertEquals(true, template.get("bool"));
		Assert.assertEquals(new ArrayList(), template.get("intarrarr"));
		Assert.assertEquals(new JSONDocTemplate(), template.get("sub_obj"));
		Assert.assertEquals(new ArrayList(), template.get("untypedlist"));
		Assert.assertEquals(new ArrayList(), template.get("subsubobjarr"));
		Assert.assertEquals(new ArrayList(), template.get("stringlist"));
		Assert.assertEquals(new ArrayList(), template.get("stringarrarr"));
		Assert.assertEquals(new ArrayList(), template.get("integerarr"));
		Assert.assertEquals(new ArrayList(), template.get("stringarr"));
		Assert.assertEquals(new ArrayList(), template.get("intarr"));
		Assert.assertEquals(new ArrayList(), template.get("subobjlist"));
		Assert.assertEquals(new ArrayList(), template.get("wildcardlist"));
		Assert.assertEquals(new ArrayList(), template.get("longlist"));
		Assert.assertEquals("", template.get("namechar"));
		Assert.assertEquals(new HashMap(), template.get("map"));
		Assert.assertEquals(new HashMap(), template.get("mapstringinteger"));
		Assert.assertEquals(new HashMap(), template.get("mapsubobjinteger"));
		Assert.assertEquals(new HashMap(), template.get("mapintegersubobj"));
		Assert.assertEquals(new HashMap(), template.get("mapintegerlistsubsubobj"));
		
		System.out.println(mapper.writeValueAsString(template));
	}

	@Test
	public void testTemplateWithConstant() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final Set<Class<?>> classes = Sets.<Class<?>>newHashSet(ClassWithConstant.class);

        final Map<String, Object> template = JSONDocTemplateBuilder.build(ClassWithConstant.class, classes);
        Assert.assertEquals("", template.get("identifier"));
        Assert.assertEquals(null, template.get(THIS_IS_A_CONSTANT));

        final String serializedTemplate =
            "{" +
                "\"identifier\":\"\"" +
            "}";

        assertThat(mapper.writeValueAsString(template), is(serializedTemplate));
	}
}
