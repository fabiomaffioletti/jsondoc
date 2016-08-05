package org.jsondoc.springmvc.issues.issue174;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <T>
 */
public class TestResponse<T> {

    /**
     * Model
     */
    private Map<String, T> data;

    /**
     * Constructor
     */
    public TestResponse() {
        this.data = new HashMap();
    }
}
