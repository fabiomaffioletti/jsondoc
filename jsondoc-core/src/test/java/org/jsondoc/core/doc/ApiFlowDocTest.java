package org.jsondoc.core.doc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsondoc.core.annotation.flow.ApiFlow;
import org.jsondoc.core.annotation.flow.ApiFlowSet;
import org.jsondoc.core.annotation.flow.ApiFlowStep;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.flow.ApiFlowDoc;
import org.jsondoc.core.scanner.DefaultJSONDocScanner;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;

public class ApiFlowDocTest {
	
	private JSONDocScanner jsondocScanner = new DefaultJSONDocScanner();

	@ApiFlowSet
	private class TestFlow {

		@ApiFlow(
				name = "flow", 
				description = "A test flow",
				steps = {
						@ApiFlowStep(apimethodid = "F1"),
						@ApiFlowStep(apimethodid = "F2"),
						@ApiFlowStep(apimethodid = "F3")
				},
				group = "Flows A"
			)
		public void flow() {
			
		}
		
		@ApiFlow(
				name = "flow2", 
				description = "A test flow 2",
				steps = {
						@ApiFlowStep(apimethodid = "F4"),
						@ApiFlowStep(apimethodid = "F5"),
						@ApiFlowStep(apimethodid = "F6")
				},
				group = "Flows B"
			)
		public void flow2() {
			
		}

	}
	
	@Test
	public void testApiDoc() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(TestFlow.class);
		
		List<ApiMethodDoc> apiMethodDocs = new ArrayList<ApiMethodDoc>();
		ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
		apiMethodDoc.setId("F1");
		apiMethodDocs.add(apiMethodDoc);
		
		Set<ApiFlowDoc> apiFlowDocs = jsondocScanner.getApiFlowDocs(classes, apiMethodDocs);
		for (ApiFlowDoc apiFlowDoc : apiFlowDocs) {
			if(apiFlowDoc.getName().equals("flow")) {
				Assert.assertEquals("A test flow", apiFlowDoc.getDescription());
				Assert.assertEquals(3, apiFlowDoc.getSteps().size());
				Assert.assertEquals("F1", apiFlowDoc.getSteps().get(0).getApimethodid());
				Assert.assertEquals("F2", apiFlowDoc.getSteps().get(1).getApimethodid());
				Assert.assertEquals("Flows A", apiFlowDoc.getGroup());
				Assert.assertNotNull(apiFlowDoc.getSteps().get(0).getApimethoddoc());
				Assert.assertEquals("F1", apiFlowDoc.getSteps().get(0).getApimethoddoc().getId());
			}
			
			if(apiFlowDoc.getName().equals("flow2")) {
				Assert.assertEquals("A test flow 2", apiFlowDoc.getDescription());
				Assert.assertEquals(3, apiFlowDoc.getSteps().size());
				Assert.assertEquals("F4", apiFlowDoc.getSteps().get(0).getApimethodid());
				Assert.assertEquals("F5", apiFlowDoc.getSteps().get(1).getApimethodid());
				Assert.assertEquals("Flows B", apiFlowDoc.getGroup());
			}
		}
	}

}
