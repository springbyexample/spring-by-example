package ua.com.springbyexample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.service.PersistenceService;


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
		assertEquals(2, employees.size());
	}

	@Test
	public void testFindEmployee() {

		Employee me = employeeService.find(1l);
		assertTrue(me.getFirstName().equals("Oleksiy"));
		assertTrue(me.getLastName().equals("Rezchykov"));
		assertTrue(me.getProject().equals("spring-by-example-1"));
		assertTrue(me.getProjectMates().size() == 1);

		Employee eugene = me.getProjectMates().iterator().next();
		assertTrue(eugene.getFirstName().equals("Eugene"));
		assertTrue(eugene.getLastName().equals("Scripnik"));
		assertTrue(eugene.getProject().equals("spring-by-example-1"));
		assertTrue(eugene.getProjectMates().size() == 1);

	}

	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("some name");
		employee.setLastName("some last name");
		employee.setProject("some project");

		Long id = employeeService.save(employee);
		Employee savedEmployee = employeeService.find(id);
		assertTrue(savedEmployee.getFirstName().equals(employee.getFirstName()));
		assertTrue(savedEmployee.getLastName().equals(employee.getLastName()));

	}

	@Test
	public void testUpdateEmployee() {
		Employee me = employeeService.find(1l);
		me.setFirstName("Alexey");
		me.setLastName("Rezchikov");
		employeeService.update(me);
		Employee anotherInstanceofMe = employeeService.find(1l);
		assertTrue(me.getFirstName().equals(anotherInstanceofMe.getFirstName()));
		assertTrue(me.getLastName().equals(anotherInstanceofMe.getLastName()));
	}

	@Test
	public void testDeleteEmpployee() {
		Employee me = employeeService.find(1l);
		employeeService.delete(me);
		Employee anotherInstanceOfMe = employeeService.find(1l);
		assertNull(anotherInstanceOfMe);

	}

}
