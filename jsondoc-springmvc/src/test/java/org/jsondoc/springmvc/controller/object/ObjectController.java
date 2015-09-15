package org.jsondoc.springmvc.controller.object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ObjectController {
	
	@RequestMapping
	public String object(@RequestBody Integer ciao) {
		return null;
	}
	
	@RequestMapping
	public String object(@RequestBody TestObject4 ciao) {
		return null;
	}
	
	@RequestMapping
	public String object(@RequestBody List<TestObject4> ciao) {
		return null;
	}
	
	@RequestMapping
	public List<String> listofstring() {
		return null;
	}
	
	@RequestMapping
	public Set<Long> setoflong() {
		return null;
	}
	
	@RequestMapping
	public TestObject testobject() {
		return null;
	}
	
	@RequestMapping
	public List<TestObject> listoftestobject() {
		return null;
	}
	
	@RequestMapping
	public List<Set<TestObject3>> listofsetoftestobject3() {
		return null;
	}
	
	@RequestMapping
	public TestObject2[] arrayoftestobject2() {
		return null;
	}
	
	@RequestMapping
	public List<TestObject>[] listofarrayoftestobject() {
		return null;
	}

	@RequestMapping
	public Map<String, String> map() {
		return null;
	}
	
	@RequestMapping
	public Map<TestObject, String> mapoftestobject() {
		return null;
	}
	
	@RequestMapping
	public Map<TestObject, TestObject2> mapoftestobjecttesobject2() {
		return null;
	}

	@RequestMapping
	public Map<Map<String, List<TestObject>>, Map<String, Set<TestObject2>>> mapoftestobjecttesobject3() {
		return null;
	}

}
