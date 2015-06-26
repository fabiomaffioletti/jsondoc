package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.*;
import org.jsondoc.core.filter.CorsFilter;
import org.jsondoc.core.pojo.*;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpringJSONDocScannerTest {

	private SpringJSONDocScanner jsondocScanner = new SpringJSONDocScanner();

	private CorsFilter corsFilterEnabled, corsFilterDisabled;

	private MockMvc mockMvcCorsEnabled, mockMvcCorsDisabled;

	@Before
	public void setup() {
		corsFilterEnabled = new CorsFilter();
		corsFilterDisabled = new CorsFilter();

		corsFilterEnabled.setCorsEnabled(true);
		mockMvcCorsEnabled = MockMvcBuilders.standaloneSetup(SpringController.class).addFilter(corsFilterEnabled)
				.build();
		corsFilterDisabled.setCorsEnabled(false);
		mockMvcCorsDisabled = MockMvcBuilders.standaloneSetup(SpringController.class).addFilter(corsFilterDisabled)
				.build();
	}

	@Api(description = "A spring controller", name = "Spring controller")
	@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
	private class SpringController {

		@ApiMethod(description = "Gets a string", path = "/wrongOnPurpose", verb = ApiVerb.GET)
		@RequestMapping(value = "/string/{name}", headers = "header=test", params = "delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public
		@ApiResponseObject
		@ResponseBody
		String string(@ApiPathParam(name = "name") @PathVariable(value = "test") String name,
				@ApiQueryParam @RequestParam("id") Integer id,
				@ApiQueryParam(name = "query", defaultvalue = "test") @RequestParam(value = "myquery") Long query,
				@RequestBody @ApiBodyObject String requestBody) {
			return "ok";
		}

		@ApiMethod(description = "ResponseStatusCodeFromSpringAnnotation", responsestatuscode = "204")
		@RequestMapping(value = "/responsestatuscodefromspringannotation")
		@ResponseStatus(value = HttpStatus.CONFLICT)
		public
		@ApiResponseObject
		@ResponseBody
		String responsestatuscodefromspringannotation() {
			return "ok";
		}

		@ApiMethod(description = "ResponseStatusCodeFromJSONDocAnnotation", responsestatuscode = "204")
		@RequestMapping(value = "/responsestatuscodefromjsondocannotation")
		public
		@ApiResponseObject
		@ResponseBody
		String responsestatuscodefromjsondocannotation() {
			return "ok";
		}

		@ApiMethod(description = "RequestHeaderManagement")
		@RequestMapping(value = "/requestheadermanagement")
		public
		@ApiResponseObject
		@ResponseBody
		String requestheadermanagement(@PathVariable("name") @ApiPathParam String name,
				@RequestHeader(value = "header") @ApiHeader(description = "", name = "") String header) {
			return "ok";
		}

		@ApiMethod(description = "ISSUE-106")
		@RequestMapping(value = "/string/{name}/{surname}", params = "delete")
		public
		@ApiResponseObject
		@ResponseBody
		String string(@ApiPathParam(name = "name") @PathVariable String name,
				@ApiPathParam @PathVariable("surname") String surname, @ApiQueryParam @RequestParam("id") Integer id,
				@ApiQueryParam(name = "age") @RequestParam Integer age) {
			return "ok";
		}

	}

	@Test
	public void testCorsEnabled() throws Exception {
		this.mockMvcCorsEnabled.perform(get("/api").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(404))
				.andExpect(header().string("Access-Control-Allow-Origin", "*"))
				.andExpect(header().string("Access-Control-Allow-Headers", "*"));
	}

	@Test(expected = AssertionError.class)
	public void testCorsDisabled() throws Exception {
		this.mockMvcCorsDisabled.perform(get("/api").accept(MediaType.APPLICATION_JSON)).andExpect(status().is(404))
				.andExpect(header().string("Access-Control-Allow-Origin", "*"))
				.andExpect(header().string("Access-Control-Allow-Headers", "*"));
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
			if (apiMethodDoc.getDescription().equals("Gets a string")) {
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

				Set<ApiParamDoc> pathparameters = apiMethodDoc.getPathparameters();
				Iterator<ApiParamDoc> ppIterator = pathparameters.iterator();
				apiParamDoc = ppIterator.next();
				apiParamDoc = apiMethodDoc.getPathparameters().iterator().next();
				Assert.assertEquals("test", apiParamDoc.getName());
			}

			if (apiMethodDoc.getDescription().equals("ResponseStatusCodeFromApiMethodAnnotation")) {
				Assert.assertEquals("409 - Conflict", apiMethodDoc.getResponsestatuscode());
			}

			if (apiMethodDoc.getDescription().equals("ResponseStatusCodeFromJSONDocAnnotation")) {
				Assert.assertEquals("204", apiMethodDoc.getResponsestatuscode());
			}

			if (apiMethodDoc.getDescription().equals("RequestHeaderManagement")) {
				Assert.assertEquals(1, apiMethodDoc.getPathparameters().size());
				Assert.assertEquals(1, apiMethodDoc.getHeaders().size());
			}

			if (apiMethodDoc.getDescription().equals("ISSUE-106")) {
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
