package org.jsondoc.core.util;

import org.jsondoc.core.pojo.ApiDoc;

import java.util.Set;

/**
 * Scans the classes for the appropriate api documentation and returns a list of all found api docs.
 *
 * @return A <code>Set<ApiDoc></code> object
 */
public interface ApiDocScanner {
    Set<ApiDoc> scan(Set<Class<?>> classes);
}
