package com.service.activity.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGenericDao<E> implements IGenericDao<E> {

	private final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		Session session;
		try {
		    session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public E findById(final Serializable id) {
		return (E) getSession().get(this.entityClass, id);
	}
	
	@Override
	public List<E> findAll() {
		return  (List<E>) getSession().createCriteria(this.entityClass).list();
	}

	@Override
	public Serializable save(E entity) {
		return getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(E entity) {
		getSession().delete(entity);
	}

	@Override
	public void deleteAll() {
		List<E> entities = findAll();
		for (E entity : entities) {
			getSession().delete(entity);
		}
	}
	
    @SuppressWarnings("rawtypes")
    public void queryForInserUpdateDelete(String hql, Map<String,Object> parameterMap) {
    	Query query = getSession().createQuery(hql);
    	parameterMap.forEach((key,value)-> query.setParameter(key, value));
    	query.executeUpdate();
    }
    
    @SuppressWarnings("rawtypes")
    public List queryForObject(String hql, Map<String,Object> parameterMap) {
    	Query query = getSession().createQuery(hql);
    	parameterMap.forEach((key,value)-> query.setParameter(key, value));
    	return query.getResultList();
    }

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public void flush() {
		getSession().flush();

	}
}
