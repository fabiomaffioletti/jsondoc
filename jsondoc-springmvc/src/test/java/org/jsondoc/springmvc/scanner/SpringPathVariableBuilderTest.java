package org.jsondoc.springmvc.scanner;

import java.util.Iterator;

import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Sets;

public class SpringPathVariableBuilderTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping
	public class SpringController {

		@RequestMapping(value = "/param-one/{id}/{string}")
		public void paramOne(@PathVariable Long id, @PathVariable("name") String name) {

		}
		
		@RequestMapping(value = "/param-one/{id}/{string}/{test}")
		public void paramTwo(@ApiPathParam(name = "id", description = "my description") @PathVariable Long id, @PathVariable("name") String name, @PathVariable @ApiPathParam Long test) {

		}

		
	}
	
	@Controller
	@RequestMapping
	public class SpringController2 {

		@RequestMapping(value = "/param-one/{id}/{string}")
		public void paramOne(@ApiPathParam(description = "description for id") @PathVariable Long id, @PathVariable("name") String name) {

		}
		
	}
	
	@Test
	public void testPathVariable() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());
		Assert.assertEquals(2, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}")) {
				Assert.assertEquals(2, apiMethodDoc.getPathparameters().size());
				Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
				ApiParamDoc id = iterator.next();
				Assert.assertEquals("", id.getName());
				Assert.assertEquals("long", id.getJsondocType().getOneLineText());
				ApiParamDoc name = iterator.next();
				Assert.assertEquals("name", name.getName());
				Assert.assertEquals("string", name.getJsondocType().getOneLineText());
			}
			
			if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}/{test}")) {
				Assert.assertEquals(3, apiMethodDoc.getPathparameters().size());
				Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
				ApiParamDoc id = iterator.next();
				Assert.assertEquals("id", id.getName());
				Assert.assertEquals("long", id.getJsondocType().getOneLineText());
				ApiParamDoc name = iterator.next();
				Assert.assertEquals("name", name.getName());
				Assert.assertEquals("string", name.getJsondocType().getOneLineText());
				ApiParamDoc test = iterator.next();
				Assert.assertEquals("", test.getName());
				Assert.assertEquals("long", test.getJsondocType().getOneLineText());
			}
		}
		
	}
	
	@Test
	public void testPathVariableWithJSONDoc() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController2.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController2", apiDoc.getName());
		Assert.assertEquals(1, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/param-one/{id}/{string}")) {
				Assert.assertEquals(2, apiMethodDoc.getPathparameters().size());
				Iterator<ApiParamDoc> iterator = apiMethodDoc.getPathparameters().iterator();
				ApiParamDoc id = iterator.next();
				Assert.assertEquals("", id.getName());
				Assert.assertEquals("long", id.getJsondocType().getOneLineText());
				Assert.assertEquals("description for id", id.getDescription());
				ApiParamDoc name = iterator.next();
				Assert.assertEquals("name", name.getName());
				Assert.assertEquals("string", name.getJsondocType().getOneLineText());
			}
		}
		
	}

}
