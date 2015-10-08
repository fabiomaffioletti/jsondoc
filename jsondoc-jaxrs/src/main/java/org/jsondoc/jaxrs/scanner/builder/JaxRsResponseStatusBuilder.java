package org.jsondoc.jaxrs.scanner.builder;

import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

public class JaxRsResponseStatusBuilder {

    public static String buildResponseStatusCode(Method method) {
        return Response.Status.OK.toString() + " - " + "OK";
    }

}
