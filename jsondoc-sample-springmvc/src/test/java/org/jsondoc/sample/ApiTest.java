package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:test-applicationContext.xml"})
public class ApiTest extends BaseControllerTest {
	
	@Test
	public void testGetJSONDoc() {
		try {
    		mockMvc.perform(get("/jsondoc")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
