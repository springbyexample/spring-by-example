package com.spring.by.example.dao;

import com.spring.by.example.domain.Employee;

public interface EmployeeDao {

	Employee find(Long id);
	Long save(Employee employee);
	void update(Employee employee);
	void delete(Employee employee);

}
