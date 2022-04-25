package com.services.employee.common;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable> {

	public T findOne(final long id);

	public List<T> findAll();

	public T create(T entity);

	public T update(T entity);

	public void delete(final T entity);

	public void deleteById(final long entityId);

	public void setClazz(Class< T > clazzToSet);
}