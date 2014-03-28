package org.jsondoc.core.doc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.util.JSONDocUtils;
import org.junit.Assert;
import org.junit.Test;

public class ApiDocTest {

	@Api(name = "test-controller", description = "a-test-controller")
	private class TestController {

		@ApiMethod(path = "/implicitResponseObject", verb = ApiVerb.GET, description = "a-test-method")
		@ApiResponseObject(Integer.class)
		public String implicitResponseObject(@ApiParam(name = "name", paramType = ApiParamType.PATH) String name, @ApiBodyObject String body) {
			return null;
		}

		@ApiMethod(path = "/name", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		String name(@ApiParam(name = "name", paramType = ApiParamType.PATH) String name, @ApiBodyObject String body) {
			return null;
		}

		@ApiMethod(path = "/age", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		Integer age(@ApiParam(name = "age", paramType = ApiParamType.PATH) Integer age, @ApiBodyObject Integer body) {
			return null;
		}

		@ApiMethod(path = "/avg", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		Long avg(@ApiParam(name = "avg", paramType = ApiParamType.PATH) Long avg, @ApiBodyObject Long body) {
			return null;
		}

		@ApiMethod(path = "/map", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		Map<String, Integer> map(@ApiParam(name = "map", paramType = ApiParamType.PATH) Map<String, Integer> map, @ApiBodyObject Map<String, Integer> body) {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@ApiMethod(path = "/unparametrizedList", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		List unparametrizedList(@ApiParam(name = "unparametrizedList", paramType = ApiParamType.PATH) List unparametrizedList, @ApiBodyObject List body) {
			return null;
		}

		@ApiMethod(path = "/parametrizedList", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		List<String> parametrizedList(@ApiParam(name = "parametrizedList", paramType = ApiParamType.PATH) List<String> parametrizedList, @ApiBodyObject List<String> body) {
			return null;
		}

		@ApiMethod(path = "/wildcardParametrizedList", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		List<?> wildcardParametrizedList(@ApiParam(name = "wildcardParametrizedList", paramType = ApiParamType.PATH) List<?> wildcardParametrizedList, @ApiBodyObject List<?> body) {
			return null;
		}

	}

	@Test
	public void testApiDoc() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestController.class);
		ApiDoc apiDoc = JSONDocUtils.getApiDocs(classes).iterator().next();
		Assert.assertEquals("test-controller", apiDoc.getName());
		Assert.assertEquals("a-test-controller", apiDoc.getDescription());

		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {

			if (apiMethodDoc.getPath().equals("/implicitResponseObject")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("integer", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getObject());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("name")) {
						Assert.assertEquals("string", apiParamDoc.getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/name")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getObject());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("name")) {
						Assert.assertEquals("string", apiParamDoc.getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/age")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("integer", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("integer", apiMethodDoc.getBodyobject().getObject());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("age")) {
						Assert.assertEquals("integer", apiParamDoc.getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/avg")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("long", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("long", apiMethodDoc.getBodyobject().getObject());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("avg")) {
						Assert.assertEquals("long", apiParamDoc.getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/map")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("map", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getMapKeyObject());
				Assert.assertEquals("integer", apiMethodDoc.getResponse().getMapValueObject());
				Assert.assertEquals("map", apiMethodDoc.getBodyobject().getObject());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getMapKeyObject());
				Assert.assertEquals("integer", apiMethodDoc.getBodyobject().getMapValueObject());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("map")) {
						Assert.assertEquals("map", apiParamDoc.getType());
						//TODO there is no check on the map key and value objects
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/parametrizedList")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("true", apiMethodDoc.getResponse().getMultiple());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getObject());
				Assert.assertEquals("true", apiMethodDoc.getBodyobject().getMultiple());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("parametrizedList")) {
						Assert.assertEquals("string", apiParamDoc.getType());
					}
				}
				
			}

			if (apiMethodDoc.getPath().equals("/unparametrizedList")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("undefined", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("true", apiMethodDoc.getResponse().getMultiple());
				Assert.assertEquals("undefined", apiMethodDoc.getBodyobject().getObject());
				Assert.assertEquals("true", apiMethodDoc.getBodyobject().getMultiple());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("unparametrizedList")) {
						Assert.assertEquals("undefined", apiParamDoc.getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/wildcardParametrizedList")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("wildcard", apiMethodDoc.getResponse().getObject());
				Assert.assertEquals("true", apiMethodDoc.getResponse().getMultiple());
				Assert.assertEquals("wildcard", apiMethodDoc.getBodyobject().getObject());
				Assert.assertEquals("true", apiMethodDoc.getBodyobject().getMultiple());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("wildcardParametrizedList")) {
						Assert.assertEquals("wildcard", apiParamDoc.getType());
					}
				}
			}

		}

	}

}
