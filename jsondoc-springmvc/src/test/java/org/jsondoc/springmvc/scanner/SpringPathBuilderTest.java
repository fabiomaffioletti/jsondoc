package org.jsondoc.springmvc.scanner;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

public class SpringPathBuilderTest {

	private JSONDocScanner jsondocScanner = new Spring3JSONDocScanner();

	@Controller
	@RequestMapping
	public class SpringController {

		@RequestMapping(value = "/path")
		public void slashPath() {

		}
		
		@RequestMapping(value = "/")
		public void path() {

		}

		@RequestMapping()
		public void none() {

		}		
	}
	
	@Controller
	@RequestMapping
	public class SpringController2 {

		@RequestMapping
		public void none() {

		}
		
		@RequestMapping(value = "/test")
		public void test() {

		}
	}
	
	@Test
	public void testPath() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());

		boolean slashPath = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().equals("/path");
			}
		});
		Assert.assertTrue(slashPath);
		
		boolean slash = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().equals("/");
			}
		});
		Assert.assertTrue(slash);
	}
	
	@Test
	public void testPath2() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController2.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController2", apiDoc.getName());

		boolean none = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().equals("/");
			}
		});
		Assert.assertTrue(none);
		
		boolean test = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().equals("/test");
			}
		});
		Assert.assertTrue(test);
	}

}
