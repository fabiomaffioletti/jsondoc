package org.jsondoc.core.util.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("data")
public abstract class AbstractJsonRootNameObject {

    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
