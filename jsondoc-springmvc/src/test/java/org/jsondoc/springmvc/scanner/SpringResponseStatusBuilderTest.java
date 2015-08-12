package org.jsondoc.springmvc.scanner;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Sets;

public class SpringResponseStatusBuilderTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping
	public class SpringController {

		@RequestMapping(value = "/status-one")
		@ResponseStatus(value = HttpStatus.CREATED)
		public String statusOne() {
			return "";
		}
		
		@RequestMapping(value = "/status-two")
		public void statusTwo() {
			
		}
		
	}
	
	@Test
	public void testApiVerb() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());
		Assert.assertEquals(2, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/status-one")) {
				Assert.assertEquals("201 - Created", apiMethodDoc.getResponsestatuscode());
			}
			if (apiMethodDoc.getPath().contains("/status-two")) {
				Assert.assertEquals("200 - OK", apiMethodDoc.getResponsestatuscode());
			}
		}
	}

}
