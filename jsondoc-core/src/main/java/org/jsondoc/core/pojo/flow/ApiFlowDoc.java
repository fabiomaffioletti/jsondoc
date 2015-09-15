package org.jsondoc.core.pojo.flow;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.flow.ApiFlow;
import org.jsondoc.core.annotation.flow.ApiFlowStep;
import org.jsondoc.core.pojo.ApiMethodDoc;

public class ApiFlowDoc implements Comparable<ApiFlowDoc> {
	public final String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private List<String> preconditions;
	private List<ApiFlowStepDoc> steps;
	private List<ApiMethodDoc> methods;
	private String group;

	public static ApiFlowDoc buildFromAnnotation(ApiFlow annotation, List<ApiMethodDoc> apiMethodDocs) {
		ApiFlowDoc apiFlowDoc = new ApiFlowDoc();
		apiFlowDoc.setDescription(annotation.description());
		apiFlowDoc.setName(annotation.name());
		apiFlowDoc.setGroup(annotation.group());
		for (String precondition : annotation.preconditions()) {
			apiFlowDoc.addPrecondition(precondition);
		}
		for (ApiFlowStep apiFlowStep : annotation.steps()) {
			ApiFlowStepDoc apiFlowStepDoc = ApiFlowStepDoc.buildFromAnnotation(apiFlowStep, apiMethodDocs);
			apiFlowDoc.addStep(apiFlowStepDoc);
			apiFlowDoc.addMethod(apiFlowStepDoc.getApimethoddoc());
		}
		return apiFlowDoc;
	}

	public ApiFlowDoc() {
		this.preconditions = new LinkedList<String>();
		this.steps = new LinkedList<ApiFlowStepDoc>();
		this.methods = new LinkedList<ApiMethodDoc>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void addStep(ApiFlowStepDoc apiFlowStepDoc) {
		this.steps.add(apiFlowStepDoc);
	}

	public List<ApiFlowStepDoc> getSteps() {
		return steps;
	}

	public void setSteps(List<ApiFlowStepDoc> steps) {
		this.steps = steps;
	}

	public List<String> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(List<String> preconditions) {
		this.preconditions = preconditions;
	}

	public void addPrecondition(String precondition) {
		this.preconditions.add(precondition);
	}

	public List<ApiMethodDoc> getMethods() {
		return methods;
	}

	public void setMethods(List<ApiMethodDoc> methods) {
		this.methods = methods;
	}
	
	public void addMethod(ApiMethodDoc method) {
		this.methods.add(method);
	}

	@Override
	public int compareTo(ApiFlowDoc o) {
		return name.compareTo(o.getName());
	}
}
