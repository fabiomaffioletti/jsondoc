package org.jsondoc.core.util;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JSONDocUtilsTest {
	private String version = "1.0";
	private String basePath = "http://localhost:8080/api";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static Logger log = Logger.getLogger(JSONDocUtilsTest.class);
	
	@Test
	public void getJSONDoc() throws JsonGenerationException, JsonMappingException, IOException {
		JSONDoc jsondoc = JSONDocUtils.getApiDoc(version, basePath, Lists.newArrayList("org.jsondoc.core.util"));
		Assert.assertEquals(1, jsondoc.getApis().size());
		Integer countApis = 0;
		for (String string : jsondoc.getApis().keySet()) {
			countApis += jsondoc.getApis().get(string).size();
		}
		Assert.assertEquals(2, countApis.intValue());
		
		Assert.assertEquals(2, jsondoc.getObjects().size());
		Integer countObjects = 0;
		for (String string : jsondoc.getObjects().keySet()) {
			countObjects += jsondoc.getObjects().get(string).size();
		}
		Assert.assertEquals(4, countObjects.intValue());
		
		log.debug(objectMapper.writeValueAsString(jsondoc));
	}
	
}