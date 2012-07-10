package ua.com.springbyexample.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ua.com.springbyexample.dao.EmployeeDao;
import ua.com.springbyexample.domain.Employee;

@Repository("employeeDao")
public class EmployeeJpaDao implements EmployeeDao {

	private final Class<Employee> entityClass = Employee.class;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Employee find(Long id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public void save(Employee employee) {
		entityManager.persist(employee);
	}

	@Override
	public void update(Employee employee) {
		entityManager.merge(employee);

	}

	@Override
	public void delete(Employee employee) {
		entityManager.remove(employee);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> find() {
		return entityManager.createQuery("from " + entityClass.getName()).getResultList();
	}

}
