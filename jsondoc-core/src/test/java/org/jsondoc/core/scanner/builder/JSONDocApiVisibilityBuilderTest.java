package org.jsondoc.core.scanner.builder;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class JSONDocApiVisibilityBuilderTest {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@Api(name = "test-type-level-visibility-and-stage", description = "Test type level visibility and stage attributes", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
	private class Controller {
		
		@ApiMethod(path = "/inherit")
		public void inherit() {
			
		}
		
		@ApiMethod(path = "/override", visibility = ApiVisibility.PRIVATE, stage = ApiStage.GA)
		public void override() {
			
		}
		
	}
	
	@Api(name = "test-method-level-visibility-and-stage", description = "Test method level visibility and stage attributes")
	private class Controller2 {
		
		@ApiMethod(path = "/only-method", visibility = ApiVisibility.PRIVATE, stage = ApiStage.DEPRECATED)
		public void testVisibilityAndStage() {
			
		}
	}
	
	@Test
	public void testApiVisibility() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(Controller.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals(ApiVisibility.PUBLIC, apiDoc.getVisibility());
		Assert.assertEquals(ApiStage.BETA, apiDoc.getStage());
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getPath().contains("/inherit")) {
				Assert.assertEquals(ApiVisibility.PUBLIC, apiMethodDoc.getVisibility());
				Assert.assertEquals(ApiStage.BETA, apiMethodDoc.getStage());
			}
			if(apiMethodDoc.getPath().contains("/override")) {
				Assert.assertEquals(ApiVisibility.PRIVATE, apiMethodDoc.getVisibility());
				Assert.assertEquals(ApiStage.GA, apiMethodDoc.getStage());
			}
		}
		
		apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(Controller2.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals(ApiVisibility.UNDEFINED, apiDoc.getVisibility());
		Assert.assertEquals(ApiStage.UNDEFINED, apiDoc.getStage());
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getPath().contains("/only-method")) {
				Assert.assertEquals(ApiVisibility.PRIVATE, apiMethodDoc.getVisibility());
				Assert.assertEquals(ApiStage.DEPRECATED, apiMethodDoc.getStage());
			}
		}
		
	}

}
