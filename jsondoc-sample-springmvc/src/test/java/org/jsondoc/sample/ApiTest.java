package org.jsondoc.sample;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.xmlConfigSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ApiTest {
	protected String contextLoc = "classpath:applicationContext.xml";
	protected String warDir = "src/main/webapp";
	protected MockMvc mockMvc = null;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = xmlConfigSetup(contextLoc).configureWebAppRootDir(warDir, false).build();
		Assert.assertNotNull(mockMvc);
	}

	@Test
	public void testLogin() {
		try {
			mockMvc.perform(get("/jsondoc")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().type(MediaType.APPLICATION_JSON_VALUE));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
