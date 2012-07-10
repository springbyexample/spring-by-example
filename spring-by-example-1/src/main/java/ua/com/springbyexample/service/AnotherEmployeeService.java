package ua.com.springbyexample.service;

import java.util.Set;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Position;


public interface AnotherEmployeeService {
	
	Set<Employee> getEmployeeListByPosition(Position position);

}
