package ua.com.springbyexample.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class EmployeeControllerTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Mock
	private EmployeeController controller;
	private AnnotationMethodHandlerAdapter adapter;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		adapter = new AnnotationMethodHandlerAdapter();

	}

	@Test
	public void testMappings() throws Exception {
		request.setRequestURI("/employee/list");
		request.setMethod("GET");
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);

		adapter.handle(request, response, controller);

		Mockito.verify(controller).listEmployees();
	}
}
