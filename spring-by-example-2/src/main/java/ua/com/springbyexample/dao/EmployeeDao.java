package ua.com.springbyexample.dao;

import ua.com.springbyexample.domain.Employee;

public interface EmployeeDao {

	Employee find(Long id);
	Long save(Employee employee);
	void update(Employee employee);
	void delete(Employee employee);

}
