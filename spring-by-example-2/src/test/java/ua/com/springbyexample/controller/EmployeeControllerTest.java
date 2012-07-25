package ua.com.springbyexample.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

	private final MockHttpServletRequest request = new MockHttpServletRequest();
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	private final AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();

	@Mock
	private EmployeeController controller;

	@Test
	public void testMappings() throws Exception {
		request.setRequestURI("/employee/list");
		request.setMethod("GET");
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);

		adapter.handle(request, response, controller);

		Mockito.verify(controller).listEmployees();
	}
}
