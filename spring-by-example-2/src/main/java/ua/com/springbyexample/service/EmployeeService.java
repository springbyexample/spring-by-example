package ua.com.springbyexample.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ua.com.springbyexample.dao.GenericDao;
import ua.com.springbyexample.domain.Employee;


@Service("employeeService")
@Transactional(readOnly = true)
public class EmployeeService extends AbtractPersistenceService<Employee, Long> {

	@Resource
	private GenericDao<Employee, Long> employeeDao;

	@Override
	protected GenericDao<Employee, Long> getDomainDAO() {
		return employeeDao;
	}

}
