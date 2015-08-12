package org.jsondoc.core.doc;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class ApiHeadersDocTest {
	
	private JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@Api(description = "ApiHeadersController", name = "ApiHeadersController")
	@ApiHeaders(
		headers = {
			@ApiHeader(name = "H1", description = "h1-description"),
			@ApiHeader(name = "H2", description = "h2-description")
		}
	)
	private class ApiHeadersController {
		
		@ApiMethod(path = "/api-headers-controller-method-one")
		public void apiHeadersMethodOne() {
			
		}
		
		@ApiMethod(path = "/api-headers-controller-method-two")
		@ApiHeaders(
			headers = {
				@ApiHeader(name = "H4", description = "h4-description"),
				@ApiHeader(name = "H1", description = "h1-description") // this is a duplicate of the one at the class level, it will not be taken into account when building the doc
			}
		)
		public void apiHeadersMethodTwo() {
			
		}
		
	}
	
	@Test
	public void testApiHeadersOnClass() {
		final ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>>newHashSet(ApiHeadersController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("ApiHeadersController", apiDoc.getName());
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getPath().contains("/api-headers-controller-method-one")) {
				Assert.assertEquals(2, apiMethodDoc.getHeaders().size());
			}
			if(apiMethodDoc.getPath().contains("/api-headers-controller-method-two")) {
				Assert.assertEquals(3, apiMethodDoc.getHeaders().size());
			}
		}
	}

}
