package com.example.www.persistence;

import com.example.www.model.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {
private Map<String,User> userDatabase = new HashMap<String,User> ();

public User getUser(String emailAddress){
	return null;
}
}
