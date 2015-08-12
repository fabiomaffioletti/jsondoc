package org.jsondoc.core.scanner.builder;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

public class JSONDocApiMethodPathBuilderTest {

	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();

	@Api(name = "test-path", description = "test-path")
	private class Controller {

		@ApiMethod(path = { "/path1", "/path2" })
		public void path() {

		}

	}

	@Test
	public void testPath() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(Controller.class), MethodDisplay.URI).iterator().next();

		boolean allRight = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().contains("/path1") && input.getPath().contains("/path2") ;
			}
			
		});
		
		Assert.assertTrue(allRight);
	}

}
