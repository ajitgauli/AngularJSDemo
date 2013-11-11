package com.example.www.dao;

import com.example.www.model.User;

import java.util.List;

public interface UserDao extends AbstractDao<User, String> {

	void saveOrUpdateUser(User user);	
	User getUserByEmail(String email);
	User getUserById(long id);
	List<User> getAllUsers();	
	void removeUser(User user);

}


