package com.spring.by.example.util;

import java.util.Set;

import com.spring.by.example.domain.Employee;

public class EmployeeUtil {
	
	private EmployeeUtil() {
		
	}
	
	public static void whoAmIAndMyBuddies(Employee employee) {
		System.out.println("Name " + employee.getFullName());
		System.out.println("Work with:" + employee.getField() + " " + employee.getTechnology() + " " + employee.getPosition() + " " + employee.getRole());
		System.out.println("Work by buddies: " + printBuddies(employee.getBuddySet()));
	}

	public static void whoAmI(Employee employee) {
		System.out.println("Name " + employee.getFullName());
	}

	private static String printBuddies(Set<Employee> buddySet) {
		StringBuilder sb = new StringBuilder();
		for (Employee buddy : buddySet) {
			sb.append("Name " + buddy.getFullName());
		}
		return sb.toString();
	}

}
