package ua.com.springbyexample.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.domain.Employee;

@Service
@Transactional(readOnly = true)
public class EmployeeSpringDataService {

	@Autowired
	private EmployeeSpringDataDao repository;

	public Employee find(Long id) {
		return repository.findOne(id);
	}

	@Transactional
	public void update(Employee employee) {
		repository.saveAndFlush(employee);
	}

	public Employee findByFirstName(String firstName) {
		return repository.findByFirstName(firstName);
	}

}
