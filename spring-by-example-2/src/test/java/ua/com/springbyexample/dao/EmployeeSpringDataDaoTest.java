package ua.com.springbyexample.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.dao.repository.EmployeeSpringDataService;
import ua.com.springbyexample.domain.Employee;

@ContextConfiguration("classpath:spring-data-context.xml")
@ActiveProfiles(profiles = "jpa")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeSpringDataDaoTest {

	private static final String FIRST_PROJECT = "spring-by-example-1";
	private static final String SECOND_PROJECT = "spring-by-example-2";

	@Autowired
	private EmployeeSpringDataService repository;

	@Test
	public void testDaoUseCases() {

		Employee me = repository.find(Long.valueOf(1l));

		Employee eugene = repository.find(Long.valueOf(2l));

		assertTrue(me.getFirstName().equals("Oleksiy"));
		assertTrue(me.getLastName().equals("Rezchykov"));

		assertTrue(eugene.getFirstName().equals("Eugene"));
		assertTrue(eugene.getLastName().equals("Scripnik"));

		assertEquals(FIRST_PROJECT, me.getProject());

		assertEquals(1, me.getProjectMates().size());

		// change current project
		me.setProject(SECOND_PROJECT);
		repository.update(me);

		Employee anotherInstanceOfMe = repository.find(Long.valueOf(1l));

		assertEquals(SECOND_PROJECT, anotherInstanceOfMe.getProject());
	}

}
