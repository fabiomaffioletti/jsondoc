package org.jsondoc.spring4mvc.scanner.field;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Arrays;
import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.springmvc.scanner.Spring4JSONDocScanner;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

public class MixedFieldWithAndWIthoutAnnotationTest {

  @Test
  public void testSpring4JSONDocScanner() {
    Spring4JSONDocScanner jsondocScanner = new Spring4JSONDocScanner();
    JSONDoc jsonDoc = jsondocScanner.getJSONDoc("1.0", "/field", Lists.newArrayList("org.jsondoc.spring4mvc.scanner.field"), true, MethodDisplay.URI);
    Assert.assertEquals(1, jsonDoc.getObjects().size());
    Set<ApiObjectDoc> objects = jsonDoc.getObjects().get("");
    ApiObjectDoc o = objects.iterator().next();
    ApiObjectFieldDoc[] fields = o.getFields().toArray(new ApiObjectFieldDoc[]{});
    Arrays.sort(fields);
    Assert.assertEquals(2, fields.length);
    Assert.assertEquals("age", fields[0].getName());
    Assert.assertEquals("id", fields[1].getName());
  }

  @RestController
  @RequestMapping(value="/spring4")
  public class Spring4RestController {
    @RequestMapping(value="/id-field/{customer-id}", method=GET)
    public Spring4ObjectField getCustomer2(@PathVariable("customer-id") Long customerId) {
      return new Spring4ObjectField();
    }
  }
  
  @ApiObject(name="Spring4ObjectField", show=true)
  public static class Spring4ObjectField {
    
    @ApiObjectField(description="Hello Dolly")
    private String id;
    
    // Without @ApiObjectField annotation
    private Long age;
    
  }
}

  
