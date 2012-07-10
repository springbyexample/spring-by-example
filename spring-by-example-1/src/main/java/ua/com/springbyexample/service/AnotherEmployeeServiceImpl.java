package ua.com.springbyexample.service;

import java.util.HashSet;
import java.util.Set;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Position;
import ua.com.springbyexample.ds.EmployeeDS;


public class AnotherEmployeeServiceImpl implements  AnotherEmployeeService {
	
	private EmployeeDS employeeDS;	

	public void setEmployeeDS(EmployeeDS employeeDS) {
		this.employeeDS = employeeDS;
	}

	@Override
	public Set<Employee> getEmployeeListByPosition(Position position) {
		Set<Employee> result  = new HashSet<Employee>();
		for (Employee employee : employeeDS.getEmployees()) {
			if (employee.getPosition() == position) {
				result.add(employee);
			}
		}
		return result;
	}

}
