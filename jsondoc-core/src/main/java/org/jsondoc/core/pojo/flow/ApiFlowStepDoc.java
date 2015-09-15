package org.jsondoc.core.pojo.flow;

import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.flow.ApiFlowStep;
import org.jsondoc.core.pojo.ApiMethodDoc;

import com.google.common.collect.Sets;

public class ApiFlowStepDoc {
	public final String jsondocId = UUID.randomUUID().toString();
	private String apimethodid;
	private ApiMethodDoc apimethoddoc;

	public static ApiFlowStepDoc buildFromAnnotation(ApiFlowStep annotation, List<ApiMethodDoc> apiMethodDocs) {
		final String ERROR_MISSING_METHOD_ID = "No method found with id: %s";
		final String HINT_MISSING_METHOD_ID = "Add the same id to both ApiMethod and ApiFlowStep";
		
		ApiFlowStepDoc apiFlowStepDoc = new ApiFlowStepDoc();
		apiFlowStepDoc.setApimethodid(annotation.apimethodid());
		for (ApiMethodDoc apiMethodDoc : apiMethodDocs) {
			if(apiMethodDoc.getId() != null && apiMethodDoc.getId().equals(annotation.apimethodid())) {
				apiFlowStepDoc.setApimethoddoc(apiMethodDoc);
			}
		}
		
		if(apiFlowStepDoc.getApimethoddoc() == null) {
			ApiMethodDoc apiMethodDoc = new ApiMethodDoc();
			apiMethodDoc.setPath(Sets.newHashSet(String.format(ERROR_MISSING_METHOD_ID, annotation.apimethodid())));
			apiMethodDoc.addJsondocerror(String.format(ERROR_MISSING_METHOD_ID, annotation.apimethodid()));
			apiMethodDoc.addJsondochint(HINT_MISSING_METHOD_ID);
			apiFlowStepDoc.setApimethoddoc(apiMethodDoc);
		}
		
		return apiFlowStepDoc;
	}

	public String getApimethodid() {
		return apimethodid;
	}

	public void setApimethodid(String apimethodid) {
		this.apimethodid = apimethodid;
	}

	public ApiMethodDoc getApimethoddoc() {
		return apimethoddoc;
	}

	public void setApimethoddoc(ApiMethodDoc apimethoddoc) {
		this.apimethoddoc = apimethoddoc;
	}

}
