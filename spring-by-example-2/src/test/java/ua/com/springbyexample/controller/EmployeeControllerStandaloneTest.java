package ua.com.springbyexample.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.service.PersistenceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerStandaloneTest {

	@Mock
	private PersistenceService<Employee, Long> employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = standaloneSetup(employeeController).build();

	}

	@Test
	public void testEmployeeList() throws Exception {

		mvc.perform(get("/employee/list")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html"))
                                          .andExpect(view().name("listEmployees"));

	}

    @Test
    public void testEmployeeJson() throws Exception {
        mvc.perform(get("/employee/rest/view/1.json")).andExpect(status().isOk());


    }
}
