package org.jsondoc.core;

import java.io.IOException;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class JSONDocUtilsTest {
	private static Reflections reflections = null;
	private String version = "1.0";
	private String basePath = "http://localhost:8080/api";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void testGetApi() {
		reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("org.jsondoc.core")));
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Api.class);
		
		JSONDoc apiDoc = new JSONDoc(version, basePath);
		apiDoc.setApis(JSONDocUtils.getApiDocs(classes));
		
		classes = reflections.getTypesAnnotatedWith(ApiObject.class);
		apiDoc.setObjects(JSONDocUtils.getApiObjectDocs(classes));
		
		try {
			System.out.println(objectMapper.writeValueAsString(apiDoc));
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}