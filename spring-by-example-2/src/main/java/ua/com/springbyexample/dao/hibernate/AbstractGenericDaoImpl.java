package ua.com.springbyexample.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("dao")
@SuppressWarnings("unchecked")
public abstract class AbstractGenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK> {
	private Class<E> entityClass;

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	protected Class<E> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	public void update(E entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public E merge(E entity) {
		return (E) sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public PK save(E entity) {
		return (PK) sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void delete(E entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public E find(PK id) {
		return (E) sessionFactory.getCurrentSession().get(getEntityClass(), id);
	}

	@Override
	public List<E> find() {
		return sessionFactory.getCurrentSession().createQuery("from " + getEntityClass().getName()).list();
	}

	@Override
	public void saveOrUpdate(E entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

}
