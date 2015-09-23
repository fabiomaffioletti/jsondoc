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

public class SpringConsumesBuilderTest {

    private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

    @Controller
    @RequestMapping
    public class SpringController {

	@RequestMapping(value = "/consumes-one", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void consumesOne() {

	}

	@RequestMapping(value = "/consumes-two", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void consumesTwo() {

	}

	@RequestMapping(value = "/consumes-three")
	public void consumesThree() {

	}

    }

    @Controller
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public class SpringController2 {

	@RequestMapping(value = "/consumes-one")
	public void consumesOne() {

	}

	@RequestMapping(value = "/consumes-two", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void consumesTwo() {

	}

	@RequestMapping(value = "/consumes-three", consumes = MediaType.APPLICATION_XML_VALUE)
	public void consumesThree() {

	}

    }

    @Test
    public void testApiVerb() {
	ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
	Assert.assertEquals("SpringController", apiDoc.getName());
	Assert.assertEquals(3, apiDoc.getMethods().size());
	for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
	    if (apiMethodDoc.getPath().contains("/consumes-one")) {
		Assert.assertEquals(1, apiMethodDoc.getConsumes().size());
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, apiMethodDoc.getConsumes().iterator().next());
	    }
	    if (apiMethodDoc.getPath().contains("/consumes-two")) {
		Assert.assertEquals(2, apiMethodDoc.getConsumes().size());
		Iterator<String> iterator = apiMethodDoc.getConsumes().iterator();
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, iterator.next());
		Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, iterator.next());
	    }
	    if (apiMethodDoc.getPath().contains("/consumes-three")) {
		Assert.assertEquals(1, apiMethodDoc.getConsumes().size());
		String consumes = apiMethodDoc.getConsumes().iterator().next();
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, consumes);
	    }
	}

	apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController2.class), MethodDisplay.URI).iterator().next();
	Assert.assertEquals("SpringController2", apiDoc.getName());
	Assert.assertEquals(3, apiDoc.getMethods().size());
	for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
	    if (apiMethodDoc.getPath().contains("/consumes-one")) {
		Assert.assertEquals(1, apiMethodDoc.getConsumes().size());
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, apiMethodDoc.getConsumes().iterator().next());
	    }
	    if (apiMethodDoc.getPath().contains("/consumes-two")) {
		Assert.assertEquals(2, apiMethodDoc.getConsumes().size());
		Iterator<String> iterator = apiMethodDoc.getConsumes().iterator();
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, iterator.next());
		Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, iterator.next());
	    }
	    if (apiMethodDoc.getPath().contains("/consumes-three")) {
		Assert.assertEquals(1, apiMethodDoc.getConsumes().size());
		Assert.assertEquals(MediaType.APPLICATION_XML_VALUE, apiMethodDoc.getConsumes().iterator().next());
	    }
	}
    }

}
