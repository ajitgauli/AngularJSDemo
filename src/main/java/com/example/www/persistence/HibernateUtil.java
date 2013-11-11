package com.example.www.persistence;
import com.example.www.service.RESTService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
 
/**
*
* @author agauli
*/
public class HibernateUtil {
 
private static final SessionFactory sessionFactory;
private static Log log = LogFactoryUtil.getLog(HibernateUtil.class);
static {
try {
// Create the SessionFactory from standard (hibernate.cfg.xml)
// config file.
sessionFactory = new Configuration().configure().buildSessionFactory();
} catch (Throwable ex) {

log.error("Initial SessionFactory creation failed." + ex);
throw new ExceptionInInitializerError(ex);
}
}
 
public static SessionFactory getSessionFactory() {
return sessionFactory;
}
 
public static Session beginTransaction() {
Session hibernateSession = getSession();
hibernateSession.beginTransaction();
return hibernateSession;
}
 
public static void commitTransaction() {
getSession().getTransaction().commit();
}
 
public static void rollbackTransaction() {
getSession().getTransaction().rollback();
}
 
public static void closeSession() {
getSession().close();
}
 
public static Session getSession() {
Session hibernateSession = sessionFactory.getCurrentSession();
return hibernateSession;
}
public static void shutdown() {
	// Close caches and connection pools
	getSessionFactory().close();
}

}