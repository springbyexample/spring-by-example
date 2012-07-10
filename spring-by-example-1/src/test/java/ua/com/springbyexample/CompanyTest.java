package ua.com.springbyexample;

import org.junit.Test;

/**
 * This test relies on new entity you need to create: Company class should contain description of some company.
 * You may add any fields you want to this entity, the only required field is company title.
 * We expect that you create new one-to-one relation between Employee entity and Company entity to reflect current company that employee works for.
 * We also expect that you create new one-to-many relation in Employee entity, it should contain companies which employee worked for in the past.
 * You need to define new companies as spring beans and assign them to Employee instances, you are free to choose XML Spring configuration or
 * Java Spring configuration to declare all new beans. 
 */
public class CompanyTest {

	@Test
	public void testTotalCompanies() {
		// we expect that CompanyService exists and it has method to retrieve all companies
		// test should check that service returns exactly 4 Company objects
	}

	@Test
	public void testOleksiyCompanies() {
		// we expect that Oleksiy is assigned to SpringByExample company currently and has 2 companies he worked for in the past
	}

	@Test
	public void testEugeneCompanies() {
		// we expect that Eugene is assigned to SpringByExample company currently and has 1 common company with Oleksiy he worked for in the past
	}

}
