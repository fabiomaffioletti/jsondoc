package org.jsondoc.springmvc.issues.issue174;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 *
 */
public class TestController {

    /**
     *
     * @param testId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/test/{testId}")
    @ResponseBody
    public TestResponse<List<TestEntity>> getTest1(@PathVariable Long testId) {
        TestResponse<List<TestEntity>> response = new TestResponse();
        return response;
    }

    /**
     *
     * @param testId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/test/{testId}")
    @ResponseBody
    public TestResponse<TestEntity> getTest2(@PathVariable Long testId) {
        TestResponse<TestEntity> response = new TestResponse();
        return response;
    }

}
