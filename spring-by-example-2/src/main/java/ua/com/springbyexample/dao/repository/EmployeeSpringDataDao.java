package ua.com.springbyexample.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.domain.Employee;

@Transactional(readOnly = true)
public interface EmployeeSpringDataDao extends JpaRepository<Employee, Long> {

}
