package org.jsondoc.springmvc.controller;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class SpringJSONDocScannerTest {
	
	private SpringJSONDocScanner jsondocScanner = new SpringJSONDocScanner();
	
	@Api(description = "A spring controller", name = "Spring controller")
	@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
	private class SpringController {
		
		@ApiMethod(description = "Gets a string", path = "/wrongOnPurpose", verb = ApiVerb.GET)
		@RequestMapping(value = "/string/{name}?id={id}&myquery={myquery}",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ApiResponseObject @ResponseBody String string(
				@ApiPathParam(name = "name") @PathVariable(value = "test") String name, 
				@ApiQueryParam(name = "id") @RequestParam Integer id,
				@ApiQueryParam(name = "query") @RequestParam(value = "myquery") Long query,
				@RequestBody @ApiBodyObject String requestBody) {
			return "ok";
		}
		
	}
	
	@Test
	public void testMergeApiDoc() {
		Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
		controllers.add(SpringController.class);
		Set<ApiDoc> apiDocs = jsondocScanner.getApiDocs(controllers);
		
		ApiDoc apiDoc = apiDocs.iterator().next();
		Assert.assertEquals("A spring controller", apiDoc.getDescription());
		Assert.assertEquals("Spring controller", apiDoc.getName());
		
		ApiMethodDoc apiMethodDoc = apiDoc.getMethods().get(0);
		Assert.assertEquals("Gets a string", apiMethodDoc.getDescription());
		Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getOneLineText());
		Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
		Assert.assertEquals("/api/string/{name}?id={id}&myquery={myquery}", apiMethodDoc.getPath());
		Assert.assertEquals("POST", apiMethodDoc.getVerb().name());
		Assert.assertEquals("application/json", apiMethodDoc.getProduces().iterator().next());
		Assert.assertEquals("application/json", apiMethodDoc.getConsumes().iterator().next());
		
		Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
		Iterator<ApiParamDoc> qpIterator = queryparameters.iterator();
		ApiParamDoc apiParamDoc = qpIterator.next();
		Assert.assertEquals("id", apiParamDoc.getName());
		Assert.assertEquals("true", apiParamDoc.getRequired());
		apiParamDoc = qpIterator.next();
		Assert.assertEquals("myquery", apiParamDoc.getName());
		Assert.assertEquals("true", apiParamDoc.getRequired());
		
		Set<ApiParamDoc> pathparameters = apiMethodDoc.getPathparameters();
		Iterator<ApiParamDoc> ppIterator = pathparameters.iterator();
		apiParamDoc = ppIterator.next();
		apiParamDoc = apiMethodDoc.getPathparameters().iterator().next();
		Assert.assertEquals("test", apiParamDoc.getName());
	}

}
