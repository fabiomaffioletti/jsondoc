package org.jsondoc.core.util;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class JSONDocUtilsTest {
	private String version = "1.0";
	private String basePath = "http://localhost:8080/api";
	private ObjectMapper objectMapper = new ObjectMapper();

	private static Logger log = Logger.getLogger(JSONDocUtilsTest.class);

	@Test
	public void getJSONDoc() throws JsonGenerationException, JsonMappingException, IOException {
		JSONDoc jsondoc = JSONDocUtils.getApiDoc(version, basePath, Lists.newArrayList("org.jsondoc.core.util"));
        final Set<ApiDoc> apis = jsondoc.getApis();
        Assert.assertEquals(2, apis.size());
		Assert.assertEquals(2, jsondoc.getObjects().size());
        for (ApiDoc api : apis) {
            if(api.getName().equals("Test1Controller")){
                Assert.assertEquals(1, api.getMethods().get(0).getQueryparameters().size());
                Assert.assertEquals(2, api.getMethods().get(0).getPathparameters().size());
            }
        }
		log.debug(objectMapper.writeValueAsString(jsondoc));
	}

}
