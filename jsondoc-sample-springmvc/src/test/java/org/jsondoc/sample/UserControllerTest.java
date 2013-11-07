package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
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
	
	@Test
    public void testGetUserByGenderAndAge() {
    	try {
    		mockMvc.perform(get("/users/q/test-name/M?agemin=5&agemax=10")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
	
	@Test
    public void testGetUserWithMapBodyObject() {
    	try {
    		Map<String, Integer> map = new HashMap<String, Integer>();
    		map.put("mapKey", 1);
    		ObjectMapper mapper = new ObjectMapper();
    		mockMvc.perform(post("/users/map")
    				.body(mapper.writeValueAsBytes(map))
    				.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }

}
