package ua.com.springbyexample;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Position;
import ua.com.springbyexample.service.AnotherEmployeeService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = EmployeeServicesJavaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServicesJavaConfigTest {

	@Autowired
	private AnotherEmployeeService anotherEmployeeService;

	@Test
	public void outputWhoAmI() {
		Set<Employee> candidates = anotherEmployeeService.getEmployeeListByPosition(Position.SENIOR);
		assertThat(candidates.size(), is(2));

        candidates.forEach(ua.com.springbyexample.util.EmployeeUtil::whoAmI);
	}

}
