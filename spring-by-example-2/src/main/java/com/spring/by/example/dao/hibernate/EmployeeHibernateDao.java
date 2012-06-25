package com.spring.by.example.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.spring.by.example.dao.EmployeeDao;
import com.spring.by.example.dao.hibernate.AbstractGenericDaoImpl;
import com.spring.by.example.domain.Employee;

@Repository("employeeHibernateDao")
public class EmployeeHibernateDao extends AbstractGenericDaoImpl<Employee, Long> implements EmployeeDao {

}
