package org.jsondoc.jaxrs.scanner.builder;

import org.jsondoc.core.pojo.ApiHeaderDoc;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arne Bosien
 */
public class JaxRsHeaderBuilder {

    public static Set<ApiHeaderDoc> buildHeaders(Method method) {
        Set<ApiHeaderDoc> headers = new LinkedHashSet<ApiHeaderDoc>();
        return headers;
    }

}
