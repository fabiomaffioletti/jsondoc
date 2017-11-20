package org.jsondoc.core.issues.issue216;

import com.google.common.collect.Lists;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Issue216Test {
	
	JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();
	
	@Test
	public void testIssue151() {
		JSONDoc jsonDoc = jsondocScanner.getJSONDoc("", "", Lists.newArrayList("org.jsondoc.core.issues.issue216"), true, MethodDisplay.URI);
		Assert.assertEquals(1, jsonDoc.getApis().keySet().size());
		Assert.assertEquals(1, jsonDoc.getApis().get("demo").size());

		ArrayList<ApiDoc> objects = new ArrayList<>();
		objects.addAll(jsonDoc.getApis().get("demo"));
		assertThat(objects.get(0).getName(), is("/stream"));
		assertThat(objects.get(0).getMethods().size(), is(1));
	}

}
