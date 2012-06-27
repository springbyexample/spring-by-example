package com.spring.by.example;

import static com.spring.by.example.util.EmployeeUtil.whoAmIAndMyBuddies;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.by.example.domain.Employee;

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
		whoAmIAndMyBuddies(eugene);
	}
}
