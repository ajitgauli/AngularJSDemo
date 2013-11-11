package com.example.www.dao.impl;

import com.example.www.dao.UserDao;
import com.example.www.model.User;
import com.example.www.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Transactional
@Repository
public class UserDaoImpl extends AbstractDaoImpl<User, String> implements UserDao {
	
	protected UserDaoImpl() {
        super(User.class);
    }	
	    
	    public void saveOrUpdateUser(User user) {
	        saveOrUpdate(user);
	    }
	    
	    public void removeUser(User user) {
	        remove(user);
	    }
	    
	    public User getUserByEmail(String email) {
	        return findByCriteria(Restrictions.eq("email", email));
	    }
	    
	    public User getUserById(long id) {
	        return findByCriteria(Restrictions.eq("id", id));
	    }
	    
	    public List<User> getAllUsers() {
	        return getAll();
	    }
	    
	    
		
}
