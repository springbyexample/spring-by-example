package com.spring.by.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.by.example.dao.hibernate.GenericDao;
import com.spring.by.example.domain.Employee;

@Service("employeeService")
public class EmployeeService extends AbtractPersistenceService<Employee, Long> {

	@Autowired
	private GenericDao<Employee, Long> employeeDao;

	@Override
	protected GenericDao<Employee, Long> getDomainDAO() {
		return employeeDao;
	}

}
