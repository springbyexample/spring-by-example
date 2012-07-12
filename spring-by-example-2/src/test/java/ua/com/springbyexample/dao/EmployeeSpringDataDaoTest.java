package ua.com.springbyexample.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

		Employee me = repository.find(1l);

		Employee eugene = repository.find(2l);

		assertThat(me.getFirstName(), equalTo("Oleksiy"));
		assertThat(me.getLastName(), equalTo("Rezchykov"));

		assertThat(eugene.getFirstName(), equalTo("Eugene"));
		assertThat(eugene.getLastName(), equalTo("Scripnik"));

		assertThat(me.getProject(), is(FIRST_PROJECT));

		assertThat(me.getProjectMates().size(), is(1));

		// change current project
		me.setProject(SECOND_PROJECT);
		repository.update(me);

		Employee anotherInstanceOfMe = repository.find(1l);

		assertThat(anotherInstanceOfMe.getProject(), equalTo(SECOND_PROJECT));
	}

}
