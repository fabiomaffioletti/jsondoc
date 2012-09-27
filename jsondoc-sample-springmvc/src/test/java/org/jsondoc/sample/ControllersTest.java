package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.http.MediaType;

public class ControllersTest extends BaseControllerTest {

	@Test
    public void testGetCity() {
    	try {
    		mockMvc.perform(get("/city/get/sydney")
					.accept(MediaType.APPLICATION_XML))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
	
	@Test
    public void testGetCountry() {
    	try {
    		mockMvc.perform(get("/country/get/australia")
					.accept(MediaType.APPLICATION_XML))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
	
	@Test
    public void testGetAllCountries() {
    	try {
    		mockMvc.perform(get("/country/all")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }

}
