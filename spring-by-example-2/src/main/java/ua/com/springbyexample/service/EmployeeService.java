package ua.com.springbyexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.springbyexample.dao.hibernate.GenericDao;
import ua.com.springbyexample.domain.Employee;


@Service("employeeService")
public class EmployeeService extends AbtractPersistenceService<Employee, Long> {

	@Autowired
	private GenericDao<Employee, Long> employeeDao;

	@Override
	protected GenericDao<Employee, Long> getDomainDAO() {
		return employeeDao;
	}

}
