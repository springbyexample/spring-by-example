package ua.com.springbyexample.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.domain.Employee;

@ContextConfiguration("/test-context.xml")
@ActiveProfiles("hibernate")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeServiceTest {

	@Resource
	private PersistenceService<Employee, Long> employeeService;

	@Test
	public void testListAllEmployees() {
		List<Employee> employees = employeeService.find();
		assertThat(employees.size(), is(2));
	}

	@Test
	public void testFindEmployee() {

		Employee me = employeeService.find(1l);
		assertThat(me.getFirstName(), equalTo("Oleksiy"));
		assertThat(me.getLastName(), equalTo("Rezchykov"));
		assertThat(me.getProject(), equalTo("spring-by-example-1"));
		assertThat(me.getProjectMates().size(), is(1));

		Employee eugene = me.getProjectMates().iterator().next();
		assertThat(eugene.getFirstName(), equalTo("Eugene"));
		assertThat(eugene.getLastName(), equalTo("Scripnik"));
		assertThat(eugene.getProject(), equalTo("spring-by-example-1"));
		assertThat(eugene.getProjectMates().size(), is(1));

	}

	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("some name");
		employee.setLastName("some last name");
		employee.setProject("some project");

		employeeService.save(employee);
		Employee savedEmployee = employeeService.find(employee.getId());
		assertThat(savedEmployee.getFirstName(), equalTo(employee.getFirstName()));
		assertThat(savedEmployee.getLastName(), equalTo(employee.getLastName()));

	}

	@Test
	public void testUpdateEmployee() {
		Employee me = employeeService.find(1l);
		me.setFirstName("Alexey");
		me.setLastName("Rezchikov");
		employeeService.update(me);
		Employee anotherInstanceofMe = employeeService.find(1l);
		assertThat(me.getFirstName(), equalTo(anotherInstanceofMe.getFirstName()));
		assertThat(me.getLastName(), equalTo(anotherInstanceofMe.getLastName()));
	}

	@Test
	public void testDeleteEmpployee() {
		Employee me = employeeService.find(1l);
		employeeService.delete(me);
		Employee anotherInstanceOfMe = employeeService.find(1l);
		assertThat(anotherInstanceOfMe, nullValue());

	}

}
