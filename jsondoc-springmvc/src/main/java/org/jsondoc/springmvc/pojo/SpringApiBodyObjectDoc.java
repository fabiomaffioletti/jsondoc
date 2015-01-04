package org.jsondoc.springmvc.pojo;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.util.JSONDocType;
import org.jsondoc.core.util.JSONDocTypeBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SpringApiBodyObjectDoc  {
    private SpringApiBodyObjectDoc() {
    }

    public static ApiBodyObjectDoc buildFromAnnotation(Method method) {
        for (Parameter parameter : method.getParameters()) {
            if (parameter.getAnnotation(RequestBody.class) != null) {
                Class<?> parameterType = parameter.getType();
                return new ApiBodyObjectDoc(JSONDocTypeBuilder.build(new JSONDocType(), parameterType, parameterType));
            }
        }
   		return null;
   	}

}
