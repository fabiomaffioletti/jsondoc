package org.jsondoc.springmvc.scanner;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PlainSpringJSONDocScannerTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
	private class SpringController {

		@RequestMapping(value = "/string/{name}", headers = "header=test", params = "delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public @ResponseBody String string(@PathVariable(value = "test") String name, @RequestParam("id") Integer id, @RequestParam Long query, @RequestHeader(value = "header-two", defaultValue = "header-test") String header, @RequestBody String requestBody) {
			return "ok";
		}

	}

	@Test
	public void testMergeApiDoc() {
		Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
		controllers.add(SpringController.class);
		Set<ApiDoc> apiDocs = jsondocScanner.getApiDocs(controllers, MethodDisplay.URI);

		ApiDoc apiDoc = apiDocs.iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getDescription());
		Assert.assertEquals("SpringController", apiDoc.getName());
		Assert.assertNotNull(apiDoc.getGroup());

		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			Assert.assertEquals(MethodDisplay.URI, apiMethodDoc.getDisplayMethodAs());
			Assert.assertNull(apiMethodDoc.getAuth());
			Assert.assertNull(apiMethodDoc.getSupportedversions());
			Assert.assertTrue(apiMethodDoc.getApierrors().isEmpty());
			Assert.assertNull(apiMethodDoc.getId());
			Assert.assertEquals("", apiMethodDoc.getSummary());
			Assert.assertEquals("", apiMethodDoc.getDescription());
			
			if (apiMethodDoc.getPath().contains("/api/string/{name}")) {
				Assert.assertEquals(2, apiMethodDoc.getHeaders().size());
				Set<ApiHeaderDoc> headers = apiMethodDoc.getHeaders();
				Iterator<ApiHeaderDoc> headersIterator = headers.iterator();
				ApiHeaderDoc headerTest = headersIterator.next();
				Assert.assertEquals("header", headerTest.getName());
				Assert.assertEquals("test", headerTest.getAllowedvalues()[0]);
				ApiHeaderDoc headerTwo = headersIterator.next();
				Assert.assertEquals("header-two", headerTwo.getName());
				Assert.assertEquals("header-test", headerTwo.getAllowedvalues()[0]);

				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getOneLineText());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
				Assert.assertEquals("POST", apiMethodDoc.getVerb().iterator().next().name());
				Assert.assertEquals("application/json", apiMethodDoc.getProduces().iterator().next());
				Assert.assertEquals("application/json", apiMethodDoc.getConsumes().iterator().next());
				Assert.assertEquals("201 - Created", apiMethodDoc.getResponsestatuscode());

				Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
				Assert.assertEquals(3, queryparameters.size());
				Iterator<ApiParamDoc> qpIterator = queryparameters.iterator();
				ApiParamDoc apiParamDoc = qpIterator.next();
				Assert.assertEquals("delete", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertEquals(null, apiParamDoc.getDefaultvalue());
				Assert.assertEquals(0, apiParamDoc.getAllowedvalues().length);
				apiParamDoc = qpIterator.next();
				Assert.assertEquals("id", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertTrue(apiParamDoc.getDefaultvalue().isEmpty());
				apiParamDoc = qpIterator.next();
				Assert.assertEquals("", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertEquals("", apiParamDoc.getDefaultvalue());

				Set<ApiParamDoc> pathparameters = apiMethodDoc.getPathparameters();
				Iterator<ApiParamDoc> ppIterator = pathparameters.iterator();
				apiParamDoc = ppIterator.next();
				apiParamDoc = apiMethodDoc.getPathparameters().iterator().next();
				Assert.assertEquals("test", apiParamDoc.getName());
			}
		}

	}

}
