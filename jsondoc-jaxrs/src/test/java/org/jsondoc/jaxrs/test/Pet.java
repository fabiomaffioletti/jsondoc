package org.jsondoc.jaxrs.test;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * @author Arne Bosien
 */
@ApiObject
public class Pet {
    @ApiObjectField(description = "name of pet")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}