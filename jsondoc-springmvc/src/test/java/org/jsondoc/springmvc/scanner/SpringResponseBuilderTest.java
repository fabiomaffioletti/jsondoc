package org.jsondoc.springmvc.scanner;

import java.util.Map;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Sets;

public class SpringResponseBuilderTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping
	public class SpringController {

		@RequestMapping(value = "/response-one")
		public String string() {
			return "";
		}
		
		@RequestMapping(value = "/response-two")
		public ResponseEntity<String> responseEntityString() {
			return ResponseEntity.ok("");
		}
		
		@RequestMapping(value = "/response-three")
		public ResponseEntity<Map<String,Integer>> responseEntityMap() {
			return ResponseEntity.ok(null);
		}
		
	}
	
	@Test
	public void testApiVerb() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());
		Assert.assertEquals(3, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/response-one")) {
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
			}
			if (apiMethodDoc.getPath().contains("/response-two")) {
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
			}
			if (apiMethodDoc.getPath().contains("/response-three")) {
				Assert.assertEquals("map[string, integer]", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
			}
		}
	}

}
