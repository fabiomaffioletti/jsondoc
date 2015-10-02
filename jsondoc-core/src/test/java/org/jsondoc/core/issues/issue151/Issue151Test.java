package org.jsondoc.core.issues.issue151;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

//@ApiObject ignored for ParameterizedType return objects
//https://github.com/fabiomaffioletti/jsondoc/issues/151
public class Issue151Test {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@Test
	public void testIssue151() {
		JSONDoc jsonDoc = jsondocScanner.getJSONDoc("", "", Lists.newArrayList("org.jsondoc.core.issues.issue151"), true, MethodDisplay.URI);
		Assert.assertEquals(2, jsonDoc.getObjects().keySet().size());
		Assert.assertEquals(1, jsonDoc.getObjects().get("bargroup").size());
		Assert.assertEquals(1, jsonDoc.getObjects().get("foogroup").size());
	}

}
