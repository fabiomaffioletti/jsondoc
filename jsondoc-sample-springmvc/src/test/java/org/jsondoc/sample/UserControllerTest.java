package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends BaseControllerTest {

	@Test
    public void testGetUserByName() {
    	try {
    		mockMvc.perform(get("/users?name=test")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }

}
