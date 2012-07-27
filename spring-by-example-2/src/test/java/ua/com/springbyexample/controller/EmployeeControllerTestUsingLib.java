package ua.com.springbyexample.controller;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.xmlConfigSetup;

import org.junit.Test;

public class EmployeeControllerTestUsingLib {

	@Test
	public void testMappings() throws Exception {

		xmlConfigSetup("classpath:mvc-test.xml").build().perform(get("/employee/list")).andExpect(status().isOk());

	}

}
