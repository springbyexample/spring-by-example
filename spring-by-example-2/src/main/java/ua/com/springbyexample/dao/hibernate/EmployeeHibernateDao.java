package ua.com.springbyexample.dao.hibernate;

import org.springframework.stereotype.Repository;

import ua.com.springbyexample.dao.EmployeeDao;
import ua.com.springbyexample.domain.Employee;

@Repository("employeeDao")
public class EmployeeHibernateDao extends AbstractGenericDaoImpl<Employee, Long> implements EmployeeDao {

}
