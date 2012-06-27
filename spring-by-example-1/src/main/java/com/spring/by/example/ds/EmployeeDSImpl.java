package com.spring.by.example.ds;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.spring.by.example.domain.Employee;
import com.spring.by.example.domain.Role;
import com.spring.by.example.domain.Technology;

@Repository
public class EmployeeDSImpl implements EmployeeDS {


	private Set<Employee> employees = new HashSet<Employee>();
	
	@Value("${me.firstName}") 
	private String myFirstName;
	@Value("${me.lastName}")
	private String myLastName;
	@Value("${me.role}")
	private String myRole;
	@Value("${me.technology}")
	private String myTechnology;

	@Value("${eugene.firstName}")
	private String eugeneFirstName;
	@Value("${eugene.lastName}")
	private String eugeneLastName;
	@Value("${eugene.role}")
	private String eugeneRole;
	@Value("${eugene.technology}")
	private String eugeneTechnology;	

	@PostConstruct
	public void buildMe() {
		Employee me = new Employee();
		me.setFirstName(myFirstName);
		me.setLastName(myLastName);
		me.setRole(Role.valueOf(myRole));
		me.setTechnology(Technology.valueOf(myTechnology));
		employees.add(me);
	}	
	
	@PostConstruct
	public void buildEugene() {
		Employee eugene = new Employee();		
		eugene.setFirstName(eugeneFirstName);
		eugene.setLastName(eugeneLastName);
		eugene.setRole(Role.valueOf(eugeneRole));
		eugene.setTechnology(Technology.valueOf(eugeneTechnology));
		employees.add(eugene); 
	}

	@Override
	public Set<Employee> getEmployees() {
		return Collections.unmodifiableSet(employees);
	}

}
