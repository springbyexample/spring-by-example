package ua.com.springbyexample.dao.hibernate;

import org.springframework.stereotype.Repository;

import ua.com.springbyexample.dao.EmployeeDao;
import ua.com.springbyexample.dao.hibernate.AbstractGenericDaoImpl;
import ua.com.springbyexample.domain.Employee;


@Repository("employeeHibernateDao")
public class EmployeeHibernateDao extends AbstractGenericDaoImpl<Employee, Long> implements EmployeeDao {

}
