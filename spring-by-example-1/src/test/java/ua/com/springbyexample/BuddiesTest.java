package ua.com.springbyexample;

import static junit.framework.Assert.assertEquals;
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
		assertEquals("Oleksiy", me.getFirstName());
		assertEquals("Rezchykov", me.getLastName());
		assertEquals(1, me.getBuddySet().size());
		
		whoAmIAndMyBuddies(eugene);
		assertEquals("Eugene", eugene.getFirstName());
		assertEquals("Scripnik", eugene.getLastName());
		assertEquals(1, eugene.getBuddySet().size());
	}
}
