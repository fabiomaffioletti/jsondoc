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

	@Controller
	@RequestMapping(value = { "/path1", "/path2" })
	public class SpringController3 {

		@RequestMapping(value = { "/path3", "/path4" })
		public void none() {

		}

	}
	
	@Controller
	@RequestMapping(value = "/path")
	public class SpringController4 {

		@RequestMapping
		public void none() {

		}

	}

	@Controller
	@RequestMapping(path = {"/path", "/path2"}, value = "/val1")
	public class SpringController5 {
		
		@RequestMapping
		public void none() {
			
		}
		
	}

	@Test
	public void testPath() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController", apiDoc.getName());

		boolean slashPath = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().contains("/path");
			}
		});
		Assert.assertTrue(slashPath);

		boolean slash = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().contains("/");
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
				System.out.println(input.getPath());
				return input.getPath().contains("/");
			}
		});
		Assert.assertTrue(none);

		boolean test = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				return input.getPath().contains("/test");
			}
		});
		Assert.assertTrue(test);
	}
	
	@Test
	public void testPath3() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController3.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController3", apiDoc.getName());

		boolean allRight = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				boolean allRight =
								input.getPath().contains("/path1/path3") && 
								input.getPath().contains("/path1/path4") && 
								input.getPath().contains("/path2/path3") && 
								input.getPath().contains("/path2/path4");     
				return allRight;
			}
		});
		Assert.assertTrue(allRight);

	}
	
	@Test
	public void testPath4() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController4.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController4", apiDoc.getName());

		boolean allRight = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				boolean allRight =
								input.getPath().contains("/path"); 
				return allRight;
			}
		});
		Assert.assertTrue(allRight);

	}

	@Test
	public void testPath5() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController5.class), MethodDisplay.URI).iterator().next();
		Assert.assertEquals("SpringController5", apiDoc.getName());
		
		boolean allRight = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				boolean allRight = input.getPath().contains("/path") && input.getPath().contains("/path2") && input.getPath().contains("/val1"); 
				return allRight;
			}
		});
		Assert.assertTrue(allRight);
	}
	
	@Test
	public void testPathWithMethodDisplayMethod() {
		ApiDoc apiDoc = jsondocScanner.getApiDocs(Sets.<Class<?>> newHashSet(SpringController5.class), MethodDisplay.METHOD).iterator().next();
		boolean allRight = FluentIterable.from(apiDoc.getMethods()).anyMatch(new Predicate<ApiMethodDoc>() {
			@Override
			public boolean apply(ApiMethodDoc input) {
				boolean allRight = input.getPath().contains("/path") && input.getPath().contains("/path2") && input.getPath().contains("/val1") && input.getDisplayedMethodString().contains("none"); 
				return allRight;
			}
		});
		Assert.assertTrue(allRight);
	}

}
