package ua.com.springbyexample.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.springbyexample.dao.GenericDao;

@Service
public abstract class AbtractPersistenceService<E, PK extends Serializable> implements PersistenceService<E, PK> {

	@Override
	@Transactional
	public void update(E entity) {
		getDomainDAO().update(entity);
	}

	@Override
	@Transactional
	public void save(E entity) {
		getDomainDAO().save(entity);
	}

	@Override
	@Transactional
	public void delete(E entity) {
		getDomainDAO().delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public E find(PK id) {
		return getDomainDAO().find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> find() {
		return getDomainDAO().find();
	}

	protected abstract GenericDao<E, PK> getDomainDAO();

}
