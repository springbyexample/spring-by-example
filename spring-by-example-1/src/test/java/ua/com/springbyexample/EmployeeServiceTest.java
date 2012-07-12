package ua.com.springbyexample;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ua.com.springbyexample.util.EmployeeUtil.whoAmI;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Role;
import ua.com.springbyexample.domain.Technology;
import ua.com.springbyexample.service.EmployeeService;

@ContextConfiguration("classpath*:annotationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void outputWhoAmI() {
		Set<Employee> candidates = employeeService.getEmployeeListByRoleAndTechology(Role.DEV, Technology.JAVA);
		assertThat(candidates.size(), is(1));

		for (Employee employee : candidates) {
			whoAmI(employee);
		}
	}

}
