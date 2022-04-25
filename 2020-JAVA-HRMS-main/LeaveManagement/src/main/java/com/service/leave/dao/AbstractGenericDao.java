package com.service.leave.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractGenericDao<E> implements IGenericDao<E> {

	private final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public E findById(final Serializable id) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Object o = new Object();
		try {
			o = session.get(this.entityClass, id);
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		}
		return (E) o;
	}

	@Override
	public Serializable save(E entity) {
		
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Serializable s =  getSession().save(entity);
		tx.commit();
		return s;
	}

	@Override
	public void saveOrUpdate(E entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(entity);
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		}
	}

	@Override
	public void delete(E entity) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(entity);
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		}
		
	}

	@Override
	public void deleteAll() {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			List<E> entities = findAll();
			for (E entity : entities) {
				session.delete(entity);
			}
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		}
	}

	

	@Override
	public List<E> findAll() {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		List<E> o = new ArrayList<>();
		try {
			o = session.createCriteria(this.entityClass).list();
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		} 
		return o;
	}
	
    private void handleException(Transaction tx) {
		tx.rollback();
		throw new RuntimeException("Data Access Layer Exception");
	}
    
    public void queryForInserUpdateDelete(String hql, Map<String,Object> parameterMap) {

    	Session session = getSession();
    	Transaction tx = session.beginTransaction();

    	try {
    		Query query = session.createQuery(hql);
    		parameterMap.forEach((key,value)-> query.setParameter(key, value));
    		query.executeUpdate();
    		tx.commit();
    	}
    	catch(Exception e) {
    		handleException(tx);
    	}
    }
    
    public List queryForObject(String hql, Map<String,Object> parameterMap) {

    	Session session = getSession();
		Transaction tx = session.beginTransaction();
		List results = new ArrayList<>();
		try {
			Query query = session.createQuery(hql);
    		parameterMap.forEach((key,value)-> query.setParameter(key, value));
			results = query.getResultList();
			tx.commit();
		}
		catch(Exception e) {
			handleException(tx);
		}
		return results;
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
