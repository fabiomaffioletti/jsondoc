package org.jsondoc.springmvc.controller;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class JSONDocController {
	private String version;
	private String basePath;
	private List<String> packages;
	private JSONDocScanner jsondocScanner;
	private boolean playgroundEnabled = true;
	private MethodDisplay displayMethodAs = MethodDisplay.URI;

	public final static String JSONDOC_DEFAULT_PATH = "/jsondoc";

	public JSONDocController(String version, String basePath, List<String> packages) {
		this.version = version;
		this.basePath = basePath;
		this.packages = packages;
		this.jsondocScanner = new SpringJSONDocScanner();
	}

	public boolean isPlaygroundEnabled() {
		return playgroundEnabled;
	}

	public void setPlaygroundEnabled(boolean playgroundEnabled) {
		this.playgroundEnabled = playgroundEnabled;
	}

	public MethodDisplay getDisplayMethodAs() {
		return displayMethodAs;
	}

	public void setDisplayMethodAs(MethodDisplay displayMethodAs) {
		this.displayMethodAs = displayMethodAs;
	}

    @RequestMapping(value = JSONDocController.JSONDOC_DEFAULT_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONDoc getApi() {
		return jsondocScanner.getJSONDoc(version, basePath, packages, playgroundEnabled, displayMethodAs);
	}

}
