package ua.com.springbyexample.service;

import java.util.Set;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Role;
import ua.com.springbyexample.domain.Technology;


public interface EmployeeService {
	
	Set<Employee> getEmployeeListByRoleAndTechnology(Role role, Technology technology);

}
