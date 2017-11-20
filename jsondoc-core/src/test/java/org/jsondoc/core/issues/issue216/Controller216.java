package org.jsondoc.core.issues.issue216;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthToken;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(name = "/stream", description = "Stream issue demo", group = "demo", visibility = ApiVisibility.PRIVATE, stage = ApiStage.RC)
@ApiAuthToken(roles = "USER", scheme = "Bearer")
@Controller
@RequestMapping(value = "/api")
public class Controller216 {

    @ApiMethod(description = "demo")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    protected @ResponseBody Object inputsGet() throws Exception {
        List<String> test = new ArrayList<>();
        test.add("x");
        List<String> collect = test.stream().filter(i -> i.equals("x")).collect(toList());
        return "done";
    }

}
