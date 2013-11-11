package com.example.www.dao;


import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, I extends Serializable> {
	
    void saveOrUpdate(E e);
    void remove(E e);
    E findByCriteria(Criterion criterion);
    List<E> getAll();
}