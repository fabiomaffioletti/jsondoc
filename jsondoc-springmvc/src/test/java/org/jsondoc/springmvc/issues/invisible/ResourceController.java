package org.jsondoc.springmvc.issues.invisible;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
	
	@RequestMapping("/resource-get")
	public ResponseEntity<ResourceInterface> get() {
		ResourceInterface ri = new ResourceImplementation();
		return ResponseEntity.ok(ri);
	}

	@RequestMapping("/resource-get-2")
	public ResourceInterface get2() {
		ResourceInterface ri = new ResourceImplementation();
		return ri;
	}

}
