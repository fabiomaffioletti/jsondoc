package org.jsondoc.spring4mvc.scanner.method;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Set;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.pojo.JSONDoc.MethodDisplay;
import org.jsondoc.springmvc.scanner.Spring4JSONDocScanner;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

public class ApiObjectFieldAnnotationOnMethodTest {

  @Test
  public void testSpring4JSONDocScanner() {
    Spring4JSONDocScanner jsondocScanner = new Spring4JSONDocScanner();
    JSONDoc jsonDoc = jsondocScanner.getJSONDoc("1.0", "/field", Lists.newArrayList("org.jsondoc.spring4mvc.scanner"), true, MethodDisplay.URI);
    for (Set<ApiObjectDoc> o : jsonDoc.getObjects().values()) {
      for (ApiObjectDoc api : o) {
        System.out.println(api.getName());
        for (ApiObjectFieldDoc field : api.getFields()) {
          System.out.println("  " + field.getName());
        }
      }
    }
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
      };
    }
  }
  
  @ApiObject(name="Spring4ObjectMethod", show=true)
  public static abstract class Spring4ObjectMethod {
    
    private String adress;
    
    @ApiObjectField
    public abstract String getName();
    
    @ApiObjectField
    public abstract Boolean setRetired();

    // Missing @ApiObjectField
    public abstract Long getLocation();
    
    @ApiObjectField
    public abstract Long getA();

    @ApiObjectField
    public abstract Long b();

    public String getAdress() {
      return adress;
    }

    public void setAdress(String adress) {
      this.adress = adress;
    }

  }
}

  
