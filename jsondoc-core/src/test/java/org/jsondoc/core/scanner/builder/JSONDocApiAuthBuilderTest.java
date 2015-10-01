package org.jsondoc.core.scanner.builder;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthToken;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class JSONDocApiAuthBuilderTest {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@Api(name = "test-token-auth", description = "Test token auth")
	@ApiAuthToken(roles = { "" }, testtokens = { "abc", "cde" })
	private class Controller {
		
		@ApiMethod(path = "/inherit")
		public void inherit() {
			
		}
		
		@ApiMethod(path = "/override")
		@ApiAuthToken(roles = { "" }, scheme = "Bearer", testtokens = { "xyz" })
		public void override() {
			
		}
		
	}
	
	@Test
	public void testApiAuthToken() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(Controller.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("TOKEN", apiDoc.getAuth().getType());
		Assert.assertEquals("", apiDoc.getAuth().getScheme());
		Assert.assertEquals("abc", apiDoc.getAuth().getTesttokens().iterator().next());
		
		for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
			if(apiMethodDoc.getPath().contains("/inherit")) {
				Assert.assertEquals("TOKEN", apiMethodDoc.getAuth().getType());
				Assert.assertEquals("", apiMethodDoc.getAuth().getScheme());
				Assert.assertEquals("abc", apiMethodDoc.getAuth().getTesttokens().iterator().next());
			}
			if(apiMethodDoc.getPath().contains("/override")) {
				Assert.assertEquals("TOKEN", apiMethodDoc.getAuth().getType());
				Assert.assertEquals("Bearer", apiMethodDoc.getAuth().getScheme());
				Assert.assertEquals("xyz", apiMethodDoc.getAuth().getTesttokens().iterator().next());
			}
		}
		
	}

}
