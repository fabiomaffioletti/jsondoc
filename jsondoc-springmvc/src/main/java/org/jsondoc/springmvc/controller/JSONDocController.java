package org.jsondoc.springmvc.controller;

import java.util.List;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.scanner.JSONDocScanner;
import org.jsondoc.springmvc.scanner.SpringJSONDocScanner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JSONDocController {
	private String version;
	private String basePath;
	private List<String> packages;
	private JSONDocScanner jsondocScanner;

	public final static String JSONDOC_DEFAULT_PATH = "/jsondoc";

	public JSONDocController(String version, String basePath, List<String> packages) {
		this.version = version;
		this.basePath = basePath;
		this.packages = packages;
		this.jsondocScanner = new SpringJSONDocScanner();
	}

	@RequestMapping(value = JSONDocController.JSONDOC_DEFAULT_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONDoc getApi() {
		return jsondocScanner.getJSONDoc(version, basePath, packages);
	}

}
