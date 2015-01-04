package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.ArrayList;
import java.util.List;

@ApiObject(name = "sample")
public class Sample {
    @ApiObjectField(description = "the id of the sample")
    private String id;
    @ApiObjectField(description = "the name of the sample")
    private String name;
    @ApiObjectField(description = "the number of the sample")
    private Integer number;
    @ApiObjectField(description = "associated data points")
    private List<String> datapoints = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getDatapoints() {
        return datapoints;
    }

    public void setDatapoints(List<String> datapoints) {
        this.datapoints = datapoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
