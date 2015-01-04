package org.jsondoc.springmvc.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiVersion;
import org.jsondoc.springmvc.annotation.SpringApiMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@Api(name = "sample", group="api", description = "manages samples")
@ApiVersion(since = "1.0")
@RestController
public class SampleController {

    @SpringApiMethod(description = "retrieve sample by id")
    @RequestMapping(value = "/samples/{id}", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody Sample getSample(@PathVariable("id") String id) {
        return new Sample();
    }

    @SpringApiMethod(description = "retrieve sample with headers")
    @RequestMapping(value = "/samplesWithHeaders", method = RequestMethod.GET)
    public @ResponseBody Sample getSampleWithHeaders(@RequestHeader("one") String one, @RequestHeader("two") String two) {
        return new Sample();
    }

    @SpringApiMethod(description = "retrieve sample by id")
    @RequestMapping(value = "/samples", method = RequestMethod.GET)
    public @ResponseBody Sample getSampleWithParam(@RequestParam("id") String id) {
        return new Sample();
    }

    @ApiVersion(since = "1.1", until = "1.1")
    @SpringApiMethod(description = "custom version for retrieving samples")
    @RequestMapping(value = "/samplesCustomVersion", method = RequestMethod.GET)
    public @ResponseBody Sample customVersion() {
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

    @SpringApiMethod(description = "get all samples with response entity")
    @RequestMapping(value = "/allsamplesWithEntities", method = RequestMethod.GET)
    public ResponseEntity<List<Sample>> allSamplesWithEntity() {
        return null;
    }

    @SpringApiMethod(description = "get sample with response entity")
    @RequestMapping(value = "/samplesEntity", method = RequestMethod.GET)
    public ResponseEntity<Sample> aSampleWithEntity() {
        return null;
    }

}
