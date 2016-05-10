package org.jsondoc.springmvc.issues.invisible;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.Spring4JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class InterfaceApiObjectTest {
	
	JSONDocScanner jsondocScanner = new Spring4JSONDocScanner();
	
	@Test
	public void testInvisible() {
		JSONDoc jsonDoc = jsondocScanner.getJSONDoc("version", "basePath", Lists.newArrayList("org.jsondoc.springmvc.issues.invisible"), true, MethodDisplay.URI);
		Assert.assertEquals(1, jsonDoc.getObjects().keySet().size());
		for (String string : jsonDoc.getObjects().keySet()) {
			Assert.assertEquals(2, jsonDoc.getObjects().get(string).size());
		}
		for (ApiDoc apiDoc : jsonDoc.getApis().get("")) {
			for (ApiMethodDoc apiMethodDoc : apiDoc.getMethods()) {
				Assert.assertEquals("Resource Interface", apiMethodDoc.getResponse().getJsondocType().getOneLineText());
			}
		}
		
	}

}
