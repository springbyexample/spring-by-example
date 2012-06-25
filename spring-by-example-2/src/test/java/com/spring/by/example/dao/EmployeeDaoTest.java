package com.spring.by.example.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.by.example.domain.Employee;

@ContextConfiguration("classpath*:jdbc-test.xml")
@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeDaoTest {

	private static final String FIRST_PROJECT = "spring-by-example-1";
	private static final String SECOND_PROJECT = "spring-by-example-2";

	@Resource
	private EmployeeDao employeeDao;

	@Test
	public void testDaoUseCases() {
		Employee me = employeeDao.find(Long.valueOf(1l));

		Employee eugene = employeeDao.find(Long.valueOf(2l));

		assertTrue(me.getFirstName().equals("Oleksiy"));
		assertTrue(me.getLastName().equals("Rezchykov"));

		assertTrue(eugene.getFirstName().equals("Eugene"));
		assertTrue(eugene.getLastName().equals("Scripnik"));

		assertEquals(FIRST_PROJECT, me.getProject());

		assertEquals(1, me.getProjectMates().size());

		// change current project
		me.setProject(SECOND_PROJECT);
		employeeDao.update(me);

		Employee anotherInstanceOfMe = employeeDao.find(Long.valueOf(1l));

		assertEquals(SECOND_PROJECT, anotherInstanceOfMe.getProject());
	}

}
