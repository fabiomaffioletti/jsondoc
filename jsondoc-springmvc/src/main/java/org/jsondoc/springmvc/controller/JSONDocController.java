package org.jsondoc.springmvc.controller;

import java.util.List;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.springmvc.util.SpringMvcDocUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/jsondoc")
public class JSONDocController {
	private String version;
	private String basePath;
	private List<String> packages;

	public void setVersion(String version) {
		this.version = version;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JSONDoc getApi() {
		return SpringMvcDocUtils.getApiDoc( version, basePath, packages );
	}
}
