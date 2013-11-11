package com.example.www.bo;

import com.example.www.dao.UserDao;
import com.example.www.model.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service(value="userManager")
public class UserManagerImpl  implements UserManager{
	
	private UserManagerImpl(){
		
	}
	
	@Autowired	
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}	
	
	public void saveOrUpdateUser(User user){
		userDao.saveOrUpdateUser(user);
	}
	
	public void removeUser(User user){
		userDao.removeUser(user);
	}
	
	public List<User> getAllUsers(){
		return userDao.getAllUsers();
	}
	
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);		
	}
	public User getUserById(long id) {
		return userDao.getUserById(id);		
	}
	
}
