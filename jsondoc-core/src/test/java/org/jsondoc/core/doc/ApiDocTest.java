package org.jsondoc.core.doc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthBasic;
import org.jsondoc.core.annotation.ApiAuthBasicUser;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.core.pojo.ApiAuthType;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiParamType;
import org.jsondoc.core.pojo.ApiVerb;
import org.jsondoc.core.util.JSONDocUtils;
import org.jsondoc.core.util.pojo.Child;
import org.jsondoc.core.util.pojo.Pizza;
import org.junit.Assert;
import org.junit.Test;

public class ApiDocTest {

	@Api(name = "test-controller", description = "a-test-controller")
	@ApiVersion(since = "1.0", until = "2.12")
	@ApiAuthNone
	private class TestController {

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
		
		@ApiMethod(path = "/LongArray", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		Long[] LongArray(@ApiParam(name = "LongArray", paramType = ApiParamType.PATH) Long[] LongArray, @ApiBodyObject Long[] body) {
			return null;
		}

		@ApiMethod(path = "/longArray", verb = ApiVerb.GET, description = "a-test-method")
		public @ApiResponseObject
		long[] longArray(@ApiParam(name = "longArray", paramType = ApiParamType.PATH) long[] LongArray, @ApiBodyObject long[] body) {
			return null;
		}
		
		@ApiMethod(path = "/version", verb = ApiVerb.GET, description = "a-test-method for api version feature")
		@ApiVersion(since = "1.0", until = "2.12")
		public @ApiResponseObject
		String version(@ApiParam(name = "version", paramType = ApiParamType.PATH) String version, @ApiBodyObject String body) {
			return null;
		}
		
		@ApiMethod(path="/child", description = "A method returning a child", verb = ApiVerb.GET)
		public @ApiResponseObject 
		Child child(@ApiParam(name = "child", paramType = ApiParamType.PATH) Child child, @ApiBodyObject Child body) {
			return null;
		}
		
		@ApiMethod(path="/pizza", description = "A method returning a pizza", verb = ApiVerb.GET)
		public @ApiResponseObject 
		Pizza pizza(@ApiParam(name = "pizza", paramType = ApiParamType.PATH) Pizza pizza, @ApiBodyObject Pizza body) {
			return null;
		}

	}
	
	@Api(name = "test-controller-with-basic-auth", description = "a-test-controller with basic auth annotation")
	@ApiAuthBasic(roles = { "ROLE_USER", "ROLE_ADMIN" }, testusers = {@ApiAuthBasicUser(username = "test-username", password = "test-password")})
	private class TestControllerWithBasicAuth {
		
		@ApiMethod(path="/basicAuth", description = "A method with basic auth", verb = ApiVerb.GET)
		@ApiAuthBasic(roles = {"ROLE_USER"}, testusers = {@ApiAuthBasicUser(username = "test-username", password = "test-password")})
		public String basicAuth() {
			return null;
		}
		
		@ApiMethod(path="/noAuth", description = "A method with no auth", verb = ApiVerb.GET)
		@ApiAuthNone
		public String noAuth() {
			return null;
		}
		
		@ApiMethod(path="/undefinedAuthWithAuthOnClass", description = "A method with undefined auth but with auth info on class declaration", verb = ApiVerb.GET)
		public String undefinedAuthWithAuthOnClass() {
			return null;
		}
		
	}
	
	@Api(name = "test-controller-with-no-auth-annotation", description = "a-test-controller with no auth annotation")
	private class TestControllerWithNoAuthAnnotation {
		
		@ApiMethod(path="/basicAuth", description = "A method with basic auth", verb = ApiVerb.GET)
		@ApiAuthBasic(roles = {"ROLE_USER"}, testusers = {@ApiAuthBasicUser(username = "test-username", password = "test-password")})
		public String basicAuth() {
			return null;
		}
		
		@ApiMethod(path="/noAuth", description = "A method with no auth", verb = ApiVerb.GET)
		@ApiAuthNone
		public String noAuth() {
			return null;
		}
		
		@ApiMethod(path="/undefinedAuthWithoutAuthOnClass", description = "A method with undefined auth and without auth info on class declaration", verb = ApiVerb.GET)
		public String undefinedAuthWithoutAuthOnClass() {
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
		Assert.assertEquals("1.0", apiDoc.getSupportedversions().getSince());
		Assert.assertEquals("2.12", apiDoc.getSupportedversions().getUntil());
		Assert.assertEquals(ApiAuthType.NONE.name(), apiDoc.getAuth().getType());
		Assert.assertEquals(JSONDocUtils.ANONYMOUS, apiDoc.getAuth().getRoles().get(0));

		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {

			if (apiMethodDoc.getPath().equals("/name")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("name")) {
						Assert.assertEquals("string", apiParamDoc.getJsondocType().getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/age")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("integer", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("integer", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("age")) {
						Assert.assertEquals("integer", apiParamDoc.getJsondocType().getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/avg")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("long", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("long", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("avg")) {
						Assert.assertEquals("long", apiParamDoc.getJsondocType().getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/map")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("map", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("string", apiMethodDoc.getResponse().getJsondocType().getMapKey().getType());
				Assert.assertEquals("integer", apiMethodDoc.getResponse().getJsondocType().getMapValue().getType());
				Assert.assertEquals("map", apiMethodDoc.getBodyobject().getJsondocType().getType());
				Assert.assertEquals("string", apiMethodDoc.getBodyobject().getJsondocType().getMapKey().getType());
				Assert.assertEquals("integer", apiMethodDoc.getBodyobject().getJsondocType().getMapValue().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("map")) {
						Assert.assertEquals("map", apiParamDoc.getJsondocType().getType());
						//TODO there is no check on the map key and value objects
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/parametrizedList")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("list of string", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("list of string", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("parametrizedList")) {
						Assert.assertEquals("list of string", apiParamDoc.getJsondocType().getType());
					}
				}
				
			}

//			if (apiMethodDoc.getPath().equals("/unparametrizedList")) {
//				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
//				Assert.assertEquals("undefined", apiMethodDoc.getResponse().getJsondocType().getJsondocType().getType());
//				Assert.assertEquals("undefined", apiMethodDoc.getBodyobject().getJsondocType().getType());
//				Assert.assertEquals("true", apiMethodDoc.getBodyobject().getMultiple());
//				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
//					if(apiParamDoc.getName().equals("unparametrizedList")) {
//						Assert.assertEquals("undefined", apiParamDoc.getType());
//					}
//				}
//			}

			if (apiMethodDoc.getPath().equals("/wildcardParametrizedList")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("list of wildcard", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("list of wildcard", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("wildcardParametrizedList")) {
						Assert.assertEquals("list of wildcard", apiParamDoc.getJsondocType().getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/LongArray")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("array of long", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("array of long", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("LongArray")) {
						Assert.assertEquals("array of long", apiParamDoc.getJsondocType().getType());
					}
				}
			}

			if (apiMethodDoc.getPath().equals("/longArray")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("array of long", apiMethodDoc.getResponse().getJsondocType().getType());
				Assert.assertEquals("array of long", apiMethodDoc.getBodyobject().getJsondocType().getType());
				for (ApiParamDoc apiParamDoc : apiMethodDoc.getPathparameters()) {
					if(apiParamDoc.getName().equals("longArray")) {
						Assert.assertEquals("array of long", apiParamDoc.getJsondocType().getType());
					}
				}
			}
			
			if (apiMethodDoc.getPath().equals("/version")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("1.0", apiMethodDoc.getSupportedversions().getSince());
				Assert.assertEquals("2.12", apiMethodDoc.getSupportedversions().getUntil());
			}
			
			if (apiMethodDoc.getPath().equals("/child")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("child", apiMethodDoc.getResponse().getJsondocType().getType());
			}
			
			if (apiMethodDoc.getPath().equals("/pizza")) {
				Assert.assertEquals(ApiVerb.GET, apiMethodDoc.getVerb());
				Assert.assertEquals("customPizzaObject", apiMethodDoc.getResponse().getJsondocType().getType());
			}

		}

		classes.clear();
		classes.add(TestControllerWithBasicAuth.class);
		apiDoc = JSONDocUtils.getApiDocs(classes).iterator().next();
		Assert.assertEquals("test-controller-with-basic-auth", apiDoc.getName());
		Assert.assertEquals(ApiAuthType.BASIC_AUTH.name(), apiDoc.getAuth().getType());
		Assert.assertEquals("ROLE_USER", apiDoc.getAuth().getRoles().get(0));
		Assert.assertEquals("ROLE_ADMIN", apiDoc.getAuth().getRoles().get(1));
		Assert.assertTrue(apiDoc.getAuth().getTestusers().size() > 0);
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().equals("/basicAuth")) {
				Assert.assertEquals(ApiAuthType.BASIC_AUTH.name(), apiMethodDoc.getAuth().getType());
				Assert.assertEquals("ROLE_USER", apiMethodDoc.getAuth().getRoles().get(0));
				Assert.assertTrue(apiMethodDoc.getAuth().getTestusers().size() > 0);
			}
			
			if (apiMethodDoc.getPath().equals("/noAuth")) {
				Assert.assertEquals(ApiAuthType.NONE.name(), apiMethodDoc.getAuth().getType());
				Assert.assertEquals(JSONDocUtils.ANONYMOUS, apiMethodDoc.getAuth().getRoles().get(0));
			}
			
			if (apiMethodDoc.getPath().equals("/undefinedAuthWithAuthOnClass")) {
				Assert.assertEquals(ApiAuthType.BASIC_AUTH.name(), apiMethodDoc.getAuth().getType());
				Assert.assertEquals("ROLE_USER", apiMethodDoc.getAuth().getRoles().get(0));
				Assert.assertEquals("ROLE_ADMIN", apiMethodDoc.getAuth().getRoles().get(1));
			}
			
		}
		
		classes.clear();
		classes.add(TestControllerWithNoAuthAnnotation.class);
		apiDoc = JSONDocUtils.getApiDocs(classes).iterator().next();
		Assert.assertEquals("test-controller-with-no-auth-annotation", apiDoc.getName());
		Assert.assertNull(apiDoc.getAuth());
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if (apiMethodDoc.getPath().equals("/basicAuth")) {
				Assert.assertEquals(ApiAuthType.BASIC_AUTH.name(), apiMethodDoc.getAuth().getType());
				Assert.assertEquals("ROLE_USER", apiMethodDoc.getAuth().getRoles().get(0));
				Assert.assertTrue(apiMethodDoc.getAuth().getTestusers().size() > 0);
			}
			
			if (apiMethodDoc.getPath().equals("/noAuth")) {
				Assert.assertEquals(ApiAuthType.NONE.name(), apiMethodDoc.getAuth().getType());
				Assert.assertEquals(JSONDocUtils.ANONYMOUS, apiMethodDoc.getAuth().getRoles().get(0));
			}
			
			if (apiMethodDoc.getPath().equals("/undefinedAuthWithoutAuthOnClass")) {
				Assert.assertNull(apiMethodDoc.getAuth());
			}
			
		}
	}

}
