package ua.com.springbyexample.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.springbyexample.domain.Employee;
import ua.com.springbyexample.domain.Role;
import ua.com.springbyexample.domain.Technology;
import ua.com.springbyexample.ds.EmployeeDS;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDS employeeDS;

	@Override
	public Set<Employee> getEmployeeListByRoleAndTechnology(Role role, Technology technology) {
		Set<Employee> result = new HashSet<Employee>();
		for (Employee employee : employeeDS.getEmployees()) {
			if (employee.getRole() == role && employee.getTechnology() == technology) {
				result.add(employee);
			}
		}
		return result;
	}

}
