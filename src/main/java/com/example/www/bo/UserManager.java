package com.example.www.bo;
import com.example.www.dao.UserDao;
import com.example.www.model.User;
import java.util.List;


public interface UserManager {	
		
	
	public void saveOrUpdateUser(User user);
	public void removeUser(User user);
	public List<User> getAllUsers();
	public User getUserByEmail(String email);
	public User getUserById(long id);
}
