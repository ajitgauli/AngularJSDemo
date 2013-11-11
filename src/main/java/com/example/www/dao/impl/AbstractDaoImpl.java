package com.example.www.dao.impl;

import com.example.www.dao.AbstractDao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E,I> {

    private Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory){
    	this.sessionFactory=sessionFactory;
    }
    @Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Transactional
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }
    
    @Transactional
    public void remove(E e) {
        getCurrentSession().delete(e);
    }

    @Transactional
    public E findByCriteria(Criterion criterion) {
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        criteria.add(criterion);
        if(criteria.list().size()>0){
        return (E) criteria.list().get(0);
        }
        else{
        	return null;
        }
    }
    
    @Transactional
    public List<E> getAll() {
    	String query = "from "+entityClass.getSimpleName();
        Query q = getCurrentSession().createQuery(query);        
        return (List<E>) q.list();
    }
}
