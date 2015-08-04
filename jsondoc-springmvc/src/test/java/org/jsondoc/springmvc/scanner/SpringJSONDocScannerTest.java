package org.jsondoc.springmvc.scanner;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SpringJSONDocScannerTest {
	
	protected JSONDocScanner jsondocScanner;
	
	@Api(description = "A spring controller", name = "Spring controller")
	@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
	protected class SpringController {
		
		@ApiMethod(description = "Gets a string", path = "/wrongOnPurpose", verb = ApiVerb.GET)
		@RequestMapping(value = "/string/{name}", headers = "header=test", params = "delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public @ApiResponseObject @ResponseBody String string(
				@ApiPathParam(name = "name") @PathVariable(value = "test") String name, 
				@ApiQueryParam @RequestParam("id") Integer id,
				@ApiQueryParam(name = "query", defaultvalue = "test") @RequestParam(value = "myquery") Long query,
				@RequestBody @ApiBodyObject String requestBody) {
			return "ok";
		}
		
		@ApiMethod(description = "ResponseStatusCodeFromSpringAnnotation", responsestatuscode = "204")
		@RequestMapping(value = "/responsestatuscodefromspringannotation")
		@ResponseStatus(value = HttpStatus.CONFLICT)
		public @ApiResponseObject @ResponseBody String responsestatuscodefromspringannotation() {
			return "ok";
		}

		@ApiMethod(description = "ResponseStatusCodeFromJSONDocAnnotation", responsestatuscode = "204")
		@RequestMapping(value = "/responsestatuscodefromjsondocannotation")
		public @ApiResponseObject @ResponseBody String responsestatuscodefromjsondocannotation() {
			return "ok";
		}
		
		@ApiMethod(description = "RequestHeaderManagement")
		@RequestMapping(value = "/requestheadermanagement")
		public @ApiResponseObject @ResponseBody String requestheadermanagement(@PathVariable("name") @ApiPathParam String name, @RequestHeader(value = "header") @ApiHeader(description = "", name = "") String header) {
			return "ok";
		}
		
		@ApiMethod(description = "ISSUE-106")
		@RequestMapping(value = "/string/{name}/{surname}", params = "delete")
		public @ApiResponseObject @ResponseBody String string(
				@ApiPathParam(name = "name") @PathVariable String name,
				@ApiPathParam @PathVariable("surname") String surname, 
				@ApiQueryParam @RequestParam("id") Integer id,
				@ApiQueryParam(name = "age") @RequestParam Integer age) {
			return "ok";
		}
		
	}

	@Before
	public void initScanner() {
		jsondocScanner = new SpringJSONDocScanner();
	}
	
	@Test
	public void testMergeApiDoc() {
		Set<Class<?>> controllers = new LinkedHashSet<Class<?>>();
		controllers.add(SpringController.class);
		Set<ApiDoc> apiDocs = jsondocScanner.getApiDocs(controllers, MethodDisplay.URI);
		
		ApiDoc apiDoc = apiDocs.iterator().next();
		Assert.assertEquals("A spring controller", apiDoc.getDescription());
		Assert.assertEquals("Spring controller", apiDoc.getName());
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getDescription().equals("Gets a string")) {
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getOneLineText());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
				Assert.assertEquals("/api/string/{name}", apiMethodDoc.getPath());
				Assert.assertEquals("POST", apiMethodDoc.getVerb().name());
				Assert.assertEquals("application/json", apiMethodDoc.getProduces().iterator().next());
				Assert.assertEquals("application/json", apiMethodDoc.getConsumes().iterator().next());
				Assert.assertEquals("201 - Created", apiMethodDoc.getResponsestatuscode());
				
				Set<ApiHeaderDoc> headers = apiMethodDoc.getHeaders();
				ApiHeaderDoc header = headers.iterator().next();
				Assert.assertEquals("header", header.getName());
				Assert.assertEquals("test", header.getAllowedvalues()[0]);
				
				Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
				Assert.assertEquals(3, queryparameters.size());
				Iterator<ApiParamDoc> qpIterator = queryparameters.iterator();
				ApiParamDoc apiParamDoc = qpIterator.next();
				Assert.assertEquals("id", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertTrue(apiParamDoc.getDefaultvalue().isEmpty());
				apiParamDoc = qpIterator.next();
				Assert.assertEquals("myquery", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertEquals("test", apiParamDoc.getDefaultvalue());
				apiParamDoc = qpIterator.next();
				Assert.assertEquals("delete", apiParamDoc.getName());
				Assert.assertEquals("true", apiParamDoc.getRequired());
				Assert.assertEquals(null, apiParamDoc.getDefaultvalue());
				Assert.assertEquals(0, apiParamDoc.getAllowedvalues().length);

				apiParamDoc = apiMethodDoc.getPathparameters().iterator().next();
				Assert.assertEquals("test", apiParamDoc.getName());
			}
			
			if(apiMethodDoc.getDescription().equals("ResponseStatusCodeFromApiMethodAnnotation")) {
				Assert.assertEquals("409 - Conflict", apiMethodDoc.getResponsestatuscode());
			}

			if(apiMethodDoc.getDescription().equals("ResponseStatusCodeFromJSONDocAnnotation")) {
				Assert.assertEquals("204", apiMethodDoc.getResponsestatuscode());
			}
			
			if(apiMethodDoc.getDescription().equals("RequestHeaderManagement")) {
				Assert.assertEquals(1, apiMethodDoc.getPathparameters().size());
				Assert.assertEquals(1, apiMethodDoc.getHeaders().size());
			}
			
			if(apiMethodDoc.getDescription().equals("ISSUE-106")) {
				Assert.assertEquals("/api/string/{name}/{surname}", apiMethodDoc.getPath());
				
				Set<ApiParamDoc> pathparameters = apiMethodDoc.getPathparameters();
				Assert.assertEquals(2, pathparameters.size());
				
				Set<ApiParamDoc> queryparameters = apiMethodDoc.getQueryparameters();
				Assert.assertEquals(3, queryparameters.size());

				Assert.assertEquals(0, apiMethodDoc.getJsondocerrors().size());
				Assert.assertEquals(0, apiMethodDoc.getJsondocwarnings().size());
				Assert.assertEquals(5, apiMethodDoc.getJsondochints().size());
			}
			
		}
		
	}

}
