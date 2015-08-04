package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Sets;

public class SpringApiHeadersDocTest {
	
	private JSONDocScanner jsondocScanner = new SpringJSONDocScanner();
	
	@Api(description = "SpringApiHeadersController", name = "SpringApiHeadersController")
	@RequestMapping(headers = { "h1", "h2" })
	private class SpringApiHeadersController {
		
		@ApiMethod
		@RequestMapping(value = "/spring-api-headers-controller-method-one")
		public void apiHeadersMethodOne() {
			
		}
		
		@ApiMethod
		@RequestMapping(value = "/spring-api-headers-controller-method-two", headers = { "h3" })
		public void apiHeadersMethodTwo() {
			
		}
		
		@ApiMethod
		@RequestMapping(value = "/spring-api-headers-controller-method-three", headers = { "h4" })
		public void apiHeadersMethodThree(@RequestHeader(value = "h5") @ApiHeader String h5) {
			
		}
		
	}
	
	@Test
	public void testApiHeadersOnClass() {
		final ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>>newHashSet(SpringApiHeadersController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringApiHeadersController", apiDoc.getName());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getPath().equals("/spring-api-headers-controller-method-one")) {
				Assert.assertEquals(2, apiMethodDoc.getHeaders().size());
			}
			if(apiMethodDoc.getPath().equals("/spring-api-headers-controller-method-two")) {
				Assert.assertEquals(3, apiMethodDoc.getHeaders().size());
			}
			if(apiMethodDoc.getPath().equals("/spring-api-headers-controller-method-three")) {
				Assert.assertEquals(4, apiMethodDoc.getHeaders().size());
			}
		}
	}

}
