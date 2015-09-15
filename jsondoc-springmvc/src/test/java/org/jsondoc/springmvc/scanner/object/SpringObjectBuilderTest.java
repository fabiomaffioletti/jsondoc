package org.jsondoc.springmvc.scanner.object;

import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.springmvc.scanner.builder.SpringObjectBuilder;
import org.jsondoc.springmvc.scanner.object.pojo.MyObject;
import org.junit.Assert;
import org.junit.Test;

public class SpringObjectBuilderTest {

	@Test
	public void testApiVerb() {
		ApiObjectDoc buildObject = SpringObjectBuilder.buildObject(MyObject.class);
		Assert.assertEquals("MyObject", buildObject.getName());
		Assert.assertEquals(3, buildObject.getFields().size());
	}

}
