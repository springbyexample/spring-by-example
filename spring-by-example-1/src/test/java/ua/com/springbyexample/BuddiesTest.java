package ua.com.springbyexample;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ua.com.springbyexample.util.EmployeeUtil.whoAmIAndMyBuddies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.com.springbyexample.domain.Employee;

@ContextConfiguration("classpath*:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BuddiesTest {

	@Autowired
	private Employee me;

	@Autowired
	private Employee eugene;

	@Test
	public void outputBuddies() {
		whoAmIAndMyBuddies(me);
		assertThat(me.getFirstName(), equalTo("Oleksiy"));
		assertThat(me.getLastName(), equalTo("Rezchykov"));
		assertThat(me.getBuddySet().size(), is(1));

		whoAmIAndMyBuddies(eugene);
		assertThat(eugene.getFirstName(), equalTo("Eugene"));
		assertThat(eugene.getLastName(), equalTo("Scripnik"));
		assertThat(eugene.getBuddySet().size(), is(1));
	}
}
