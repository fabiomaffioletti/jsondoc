package org.jsondoc.core.doc;

import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiVerb;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class ApiMethodDocTest {

	private ApiMethodDoc first;
	private ApiMethodDoc second;

	@Test
	public void testNotEqual() {
		first = new ApiMethodDoc();
		first.setPath(Sets.newHashSet("/first"));
		first.setVerb(Sets.newHashSet(ApiVerb.GET));
		second = new ApiMethodDoc();
		second.setPath(Sets.newHashSet("/second"));
		second.setVerb(Sets.newHashSet(ApiVerb.GET));
		Assert.assertNotEquals(0, first.compareTo(second));
	}

	@Test
	public void testEqual() {
		first = new ApiMethodDoc();
		first.setPath(Sets.newHashSet("/test"));
		first.setVerb(Sets.newHashSet(ApiVerb.GET));
		second = new ApiMethodDoc();
		second.setPath(Sets.newHashSet("/test"));
		second.setVerb(Sets.newHashSet(ApiVerb.GET));
		Assert.assertEquals(0, first.compareTo(second));
	}
	
	@Test
	public void testNotEqualMultipleVerbs() {
		first = new ApiMethodDoc();
		first.setPath(Sets.newHashSet("/first"));
		first.setVerb(Sets.newHashSet(ApiVerb.GET, ApiVerb.POST));
		second = new ApiMethodDoc();
		second.setPath(Sets.newHashSet("/second"));
		second.setVerb(Sets.newHashSet(ApiVerb.GET, ApiVerb.POST));
		Assert.assertNotEquals(0, first.compareTo(second));
		
		second.setPath(Sets.newHashSet("/first"));
		second.setVerb(Sets.newHashSet(ApiVerb.PUT, ApiVerb.POST));
		Assert.assertNotEquals(0, first.compareTo(second));
	}
	
	@Test
	public void testEqualMultipleVerbs() {
		first = new ApiMethodDoc();
		first.setPath(Sets.newHashSet("/test"));
		first.setVerb(Sets.newHashSet(ApiVerb.GET, ApiVerb.POST));
		second = new ApiMethodDoc();
		second.setPath(Sets.newHashSet("/test"));
		second.setVerb(Sets.newHashSet(ApiVerb.GET, ApiVerb.POST));
		Assert.assertEquals(0, first.compareTo(second));
		
		second.setVerb(Sets.newHashSet(ApiVerb.POST, ApiVerb.GET));
		Assert.assertEquals(0, first.compareTo(second));
	}

}
