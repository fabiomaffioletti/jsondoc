package org.jsondoc.core.util;

import org.jsondoc.core.pojo.ApiDoc;

import java.util.Set;

public interface ApiDocScanner {
    Set<ApiDoc> scan(Set<Class<?>> classes);
}
