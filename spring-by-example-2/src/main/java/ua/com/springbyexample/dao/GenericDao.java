package ua.com.springbyexample.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<E, PK extends Serializable> {
	void update(E entity);

	void save(E entity);

	void delete(E entity);

	E find(PK id);

	List<E> find();

}
