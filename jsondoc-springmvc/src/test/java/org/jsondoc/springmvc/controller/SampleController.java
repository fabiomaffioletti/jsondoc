package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.springframework.web.bind.annotation.*;

@Api(name = "sample", group="api", description = "manages samples")
@ApiVersion(since = "1.0")
@RestController
public class SampleController {

    @SpringApiMethod(description = "retrieve sample by id")
    @RequestMapping(value = "/samples/{id}", method = RequestMethod.GET)
    public @ResponseBody Sample getSample(@PathVariable("id") String id) {
        return new Sample();
    }

}
