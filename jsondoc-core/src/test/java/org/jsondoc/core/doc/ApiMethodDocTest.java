package org.jsondoc.core.doc;

import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.junit.Assert;
import org.junit.Test;

public class ApiMethodDocTest {

	private ApiMethodDoc first;
	private ApiMethodDoc second;

	@Test
	public void testNotEqual() {
		first = new ApiMethodDoc();
		first.setPath("/first");
		first.setVerb(new ApiVerb[] { ApiVerb.GET });
		second = new ApiMethodDoc();
		second.setPath("/second");
		second.setVerb(new ApiVerb[] { ApiVerb.GET });
		Assert.assertNotEquals(0, first.compareTo(second));
	}

	@Test
	public void testEqual() {
		first = new ApiMethodDoc();
		first.setPath("/test");
		first.setVerb(new ApiVerb[] { ApiVerb.GET });
		second = new ApiMethodDoc();
		second.setPath("/test");
		second.setVerb(new ApiVerb[] { ApiVerb.GET });
		Assert.assertEquals(0, first.compareTo(second));
	}
	
	@Test
	public void testNotEqualMultipleVerbs() {
		first = new ApiMethodDoc();
		first.setPath("/first");
		first.setVerb(new ApiVerb[] { ApiVerb.GET, ApiVerb.POST });
		second = new ApiMethodDoc();
		second.setPath("/second");
		second.setVerb(new ApiVerb[] { ApiVerb.GET, ApiVerb.POST });
		Assert.assertNotEquals(0, first.compareTo(second));
		
		second.setPath("/first");
		second.setVerb(new ApiVerb[] { ApiVerb.PUT, ApiVerb.POST });
		Assert.assertNotEquals(0, first.compareTo(second));
	}
	
	@Test
	public void testEqualMultipleVerbs() {
		first = new ApiMethodDoc();
		first.setPath("/test");
		first.setVerb(new ApiVerb[] { ApiVerb.GET, ApiVerb.POST });
		second = new ApiMethodDoc();
		second.setPath("/test");
		second.setVerb(new ApiVerb[] { ApiVerb.GET, ApiVerb.POST });
		Assert.assertEquals(0, first.compareTo(second));
		
		second.setVerb(new ApiVerb[] { ApiVerb.POST, ApiVerb.GET});
		Assert.assertEquals(0, first.compareTo(second));
	}

}
