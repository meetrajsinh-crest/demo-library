package com.service.leave.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<E> {
	
	Serializable save(E entity);
	
	public void saveOrUpdate(E entity);
	
	void delete( E entity );
	
	void deleteAll();
	
	List<E> findAll();
	
	E findById( Serializable id );
	
	void clear();
	
	void flush();
	
}
