package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllersTest extends BaseControllerTest {

	@Test
    public void testGetCityByName() {
    	try {
    		mockMvc.perform(get("/cities/name/sydney")
					.accept(MediaType.APPLICATION_XML))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
	
	@Test
    public void testDeleteCity() {
    	try {
    		mockMvc.perform(delete("/cities/1"))
					.andDo(print())
					.andExpect(status().isNoContent());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }
	
	@Test
    public void testGetCityById() {
    	try {
    		mockMvc.perform(get("/cities/1"))
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
    		mockMvc.perform(get("/countries/australia")
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
    		mockMvc.perform(get("/countries")
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
    }

}
