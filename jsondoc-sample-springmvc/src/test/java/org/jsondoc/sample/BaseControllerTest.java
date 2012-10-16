package org.jsondoc.sample;

import static org.springframework.test.web.server.setup.MockMvcBuilders.xmlConfigSetup;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;

@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
public class BaseControllerTest {

    protected String contextLoc = "classpath:test-applicationContext.xml";
    protected String warDir = "src/main/webapp";
    protected MockMvc mockMvc = null;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mockMvc = xmlConfigSetup(contextLoc).configureWebAppRootDir(warDir, false).build();
        Assert.assertNotNull(mockMvc);
    }

    protected void printBodyJSON(Object json) throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("#################################################################");
        System.out.println("#################### JSON BODY OBJECT ###########################");
        System.out.println(objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true).writeValueAsString(json));
        System.out.println("#################################################################");
        System.out.println("#################################################################");
        System.out.println("\n");
        System.out.println("\n");
    }
}