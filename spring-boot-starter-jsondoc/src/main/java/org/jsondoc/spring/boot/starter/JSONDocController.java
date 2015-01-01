package org.jsondoc.spring.boot.starter;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONDocController {

	private String version;

	private String basePath;

	private List<String> packages = new ArrayList<String>();

	public JSONDocController(String version, String basePath, List<String> packages) {
		this.version = version;
		this.basePath = basePath;
		this.packages = packages;
	}

	@RequestMapping(value = JSONDocConfig.JSONDOC_REQUEST_MAPPING, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public JSONDoc jsondoc() {
		return JSONDocUtils.getApiDoc(version, basePath, packages);
	}

}
