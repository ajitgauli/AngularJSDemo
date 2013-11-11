package com.example.www.util;

import com.example.www.model.User;
import com.example.www.model.UserWrapper4CSV;
import com.example.www.model.UserWrapper4Edit;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtil {
	
	public static String getJSON(Object o){
		Gson gson = new Gson();
		return gson.toJson(o);
	}
	
	public static JsonObject getJsonObject(String jsonString){
		
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		reader.setLenient(true);
		JsonParser  parser = new JsonParser();		
		JsonElement elem=parser.parse(reader);         
        return elem.getAsJsonObject();       
	}
	
	public static List<UserWrapper4CSV> getUsers4CSV(List<User> users){
		List<UserWrapper4CSV> users4CSV = new ArrayList<UserWrapper4CSV>();
		if(Validator.isNotNull(users)){
			for(User user:users){
				UserWrapper4CSV user4CSV = new UserWrapper4CSV(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhone());
				users4CSV.add(user4CSV);
			}
		}
		else{
			return null;
		}
		return users4CSV;
	}
	public static UserWrapper4Edit getUser4Edit(User user){
		UserWrapper4Edit user4Edit=null;
		if(Validator.isNotNull(user)){			
				user4Edit = new UserWrapper4Edit(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhone());
				user4Edit.setId(user.getId());
		}
		
		return user4Edit;
	}	
	
	private static Log log = LogFactoryUtil.getLog(ServiceUtil.class);
}
