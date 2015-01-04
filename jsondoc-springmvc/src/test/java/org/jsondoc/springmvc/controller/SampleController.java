package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@Api(name = "sample", group="api", description = "manages samples")
@ApiVersion(since = "1.0")
@RestController
public class SampleController {

    @SpringApiMethod(description = "retrieve sample by id")
    @RequestMapping(value = "/samples/{id}", method = RequestMethod.GET)
    public @ResponseBody Sample getSample(@PathVariable("id") String id) {
        return new Sample();
    }

    @SpringApiMethod(description = "retrieve sample by id")
    @RequestMapping(value = "/samples", method = RequestMethod.GET)
    public @ResponseBody Sample getSampleWithParam(@RequestParam("id") String id) {
        return new Sample();
    }

    @SpringApiMethod(description = "create sample")
    @RequestMapping(value = "/samples", method = RequestMethod.POST)
    public @ResponseBody Sample postWithRequestBody(@RequestBody Sample sample) {
        return sample;
    }

    @SpringApiMethod(description = "get all samples")
    @RequestMapping(value = "/allsamples", method = RequestMethod.GET)
    public @ResponseBody List<Sample> allSamples() {
        return asList(new Sample());
    }

    //todo: next, handle response entity collections
//    @SpringApiMethod(description = "get all samples with response entity")
//    @RequestMapping(value = "/allsamplesWithEntities", method = RequestMethod.GET)
//    public ResponseEntity<List<Sample>> allSamplesWithEntity() {
//        return null;
//    }

    @SpringApiMethod(description = "get sample with response entity")
    @RequestMapping(value = "/samplesEntity", method = RequestMethod.GET)
    public ResponseEntity<Sample> aSampleWithEntity() {
        return null;
    }

}
