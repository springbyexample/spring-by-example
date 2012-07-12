package ua.com.springbyexample.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.domain.Employee;

@ContextConfiguration("classpath:test-context.xml")
@ActiveProfiles(profiles = "jpa")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeDaoTest {

	private static final String FIRST_PROJECT = "spring-by-example-1";
	private static final String SECOND_PROJECT = "spring-by-example-2";

	@Resource
	private EmployeeDao employeeDao;

	@Test
	public void testDaoUseCases() {

		Employee me = employeeDao.find(1l);

		Employee eugene = employeeDao.find(2l);

		assertThat(me.getFirstName(), equalTo("Oleksiy"));
		assertThat(me.getLastName(), equalTo("Rezchykov"));

		assertThat(eugene.getFirstName(), equalTo("Eugene"));
		assertThat(eugene.getLastName(), equalTo("Scripnik"));

		assertThat(me.getProject(), equalTo(FIRST_PROJECT));

		assertThat(me.getProjectMates().size(), is(1));

		// change current project
		me.setProject(SECOND_PROJECT);
		employeeDao.update(me);

		Employee anotherInstanceOfMe = employeeDao.find(1l);

		assertThat(anotherInstanceOfMe.getProject(), equalTo(SECOND_PROJECT));
	}

}
