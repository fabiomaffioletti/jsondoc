package org.jsondoc.springmvc.scanner;

import java.util.Iterator;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Sets;

public class SpringProducesBuilderTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping
	public class SpringController {

		@RequestMapping(value = "/produces-one", produces = MediaType.APPLICATION_JSON_VALUE)
		public void producesOne() {

		}
		
		@RequestMapping(value = "/produces-two", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public void producesTwo() {

		}
		
		@RequestMapping(value = "/produces-three")
		public void producesThree() {

		}

	}
	
	@Controller
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public class SpringController2 {

		@RequestMapping(value = "/produces-one")
		public void producesOne() {

		}
		
		@RequestMapping(value = "/produces-two", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public void producesTwo() {

		}
		
		@RequestMapping(value = "/produces-three", produces = MediaType.APPLICATION_XML_VALUE)
		public void producesThree() {

		}

	}

	@Test
	public void testApiVerb() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());
		Assert.assertEquals(3, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/produces-one")) {
				Assert.assertEquals(1, apiMethodDoc.getProduces().size());
				Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, apiMethodDoc.getProduces().iterator().next());
			}
			if (apiMethodDoc.getPath().contains("/produces-two")) {
				Assert.assertEquals(2, apiMethodDoc.getProduces().size());
				Iterator<String> iterator = apiMethodDoc.getProduces().iterator();
				Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, iterator.next());
				Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, iterator.next());
			}
			if (apiMethodDoc.getPath().contains("/produces-three")) {
				Assert.assertEquals(1, apiMethodDoc.getProduces().size());
				String produces = apiMethodDoc.getProduces().iterator().next();
				Assert.assertEquals("application/json", produces);
			}
		}
		
		apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController2.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController2", apiDoc.getName());
		Assert.assertEquals(3, apiDoc.getMethods().size());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().contains("/produces-one")) {
				Assert.assertEquals(1, apiMethodDoc.getProduces().size());
				Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, apiMethodDoc.getProduces().iterator().next());
			}
			if (apiMethodDoc.getPath().contains("/produces-two")) {
				Assert.assertEquals(2, apiMethodDoc.getProduces().size());
				Iterator<String> iterator = apiMethodDoc.getProduces().iterator();
				Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, iterator.next());
				Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, iterator.next());
			}
			if (apiMethodDoc.getPath().contains("/produces-three")) {
				Assert.assertEquals(1, apiMethodDoc.getProduces().size());
				Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, apiMethodDoc.getProduces().iterator().next());
			}
		}
	}

}
