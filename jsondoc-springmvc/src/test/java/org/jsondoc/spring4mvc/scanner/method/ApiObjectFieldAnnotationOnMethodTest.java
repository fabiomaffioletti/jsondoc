package org.jsondoc.spring4mvc.scanner.method;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

public class ApiObjectFieldAnnotationOnMethodTest {

  @Test
  public void testSpring4JSONDocScanner() {
    Spring4JSONDocScanner jsondocScanner = new Spring4JSONDocScanner();
    JSONDoc jsonDoc = jsondocScanner.getJSONDoc("1.0", "/field", Lists.newArrayList("org.jsondoc.spring4mvc.scanner.method"), true, MethodDisplay.URI);
    Assert.assertEquals(1, jsonDoc.getObjects().size());
    Set<ApiObjectDoc> objects = jsonDoc.getObjects().get("");
    ApiObjectDoc o = objects.iterator().next();
    ApiObjectFieldDoc[] fields = o.getFields().toArray(new ApiObjectFieldDoc[]{});
    Arrays.sort(fields);
    Assert.assertEquals(6, fields.length);
    Assert.assertEquals("a", fields[0].getName());
    Assert.assertEquals("address", fields[1].getName());
    Assert.assertEquals("b", fields[2].getName());
    Assert.assertEquals("get", fields[3].getName());
    Assert.assertEquals("name", fields[4].getName());
    Assert.assertEquals("retired", fields[5].getName());
  }

  @RestController
  @RequestMapping(value="/spring4")
  public class Spring4RestController {
  
    @RequestMapping(value="/method", method=GET)
    public Spring4ObjectMethod getMethod() {
      return new Spring4ObjectMethod() {
        @Override
        public String getName() { return null; }
        @Override
        public Long getLocation() { return null; }
        @Override
        public Long getA() { return null; }
        @Override
        public Long b() { return null; }
        @Override
        public Boolean setRetired() { return null; }
        @Override
        public Boolean get() { return null; }
      };
    }
  }
  
  @ApiObject(name="Spring4ObjectMethod", show=true)
  public static abstract class Spring4ObjectMethod {
    
    private String address;
    
    @ApiObjectField
    public abstract String getName();
    
    @ApiObjectField
    public abstract Boolean setRetired();

    @ApiObjectField
    public abstract Boolean get();

    // Missing @ApiObjectField
    public abstract Long getLocation();
    
    @ApiObjectField
    public abstract Long getA();

    @ApiObjectField
    public abstract Long b();

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }
  }
}

  
