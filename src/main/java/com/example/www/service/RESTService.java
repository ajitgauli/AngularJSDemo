package com.example.www.service;

import com.example.www.bo.UserManager;
import com.example.www.model.User;
import com.example.www.model.UserWrapper4CSV;
import com.example.www.model.UserWrapper4Edit;
import com.example.www.util.AppConstants;
import com.example.www.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Component 
@Path("/service")
public class RESTService {
	@Autowired	
	private UserManager userManager;
	private String response="";	
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {		
		List<User> users = userManager.getAllUsers();
		
		if(users != null){
			//jersey does not have support for partial response yet, so creating a partial response here	
			List<UserWrapper4CSV> users4CSV = ServiceUtil.getUsers4CSV(users);
		return Response.status(200).entity(users4CSV.toArray()).build();
		}
		else{
			return Response.status(200).entity("ERROR").build();	
		}
	}
	
	@POST
	@Path("/saveOrUpdateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_HTML})
	public String saveOrUpdateUser(User user) {
		
		String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();
        String newPassword = user.getNewPassword();
        String newPasswordConfirm = user.getNewPasswordConfirm();
        String email = user.getEmail();
        String phone = user.getPhone();
        long id = user.getId(); 
        
		log.info("firstName:"+firstName);
        log.info("lastName:"+lastName);
        log.info("password:"+password);
        log.info("newPassword:"+newPassword);
        log.info("newPasswordConfirm:"+newPasswordConfirm);
        log.info("email:"+email);
        log.info("phone:"+phone);
        log.info("id:"+id);
        
       boolean valid = validateUser(user);
       boolean uniqueEmail;       
       boolean passwordValidation=true;
       //this is user edit
       if(Validator.isNotNull(id) && id!=0){
    	  User userInDb = getUserById(id);
    	 uniqueEmail = hasUniqueEmail(email,userInDb);
    	 passwordValidation = validatePassword(userInDb.getPassword(),user.getPassword(),user.getNewPassword(),user.getNewPasswordConfirm(),user);
       }       
       //this is new user
       else{
    	  uniqueEmail = hasUniqueEmail(email);   
       }
       
       if(valid && passwordValidation){
	   if(uniqueEmail){
       //String output = ServiceUtil.getJSON(user);	   
	   userManager.saveOrUpdateUser(user);
		return "SUCCESS";
	   }
	   else{
		return "EMAIL ALREADY EXISTS";   
	   }
       }
       else{
    	   return response;
       }
 
	}	
	
	@POST
	@Path("/removeUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeUser(User user) {		   
	   userManager.removeUser(user);
	   return "SUCCESS"; 
 
	}	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	public Response login(String jsonString) {
		log.info("jsonString: "+jsonString);
		ServiceUtil.getJsonObject(jsonString);
		JsonObject jsonObject = ServiceUtil.getJsonObject(jsonString);
		String email = jsonObject.get("email").getAsString();
		String password = jsonObject.get("password").getAsString();
        log.info("email:"+email);
        log.info("password:"+password);        
        User user = userManager.getUserByEmail(email);
        if(Validator.isNull(user)){
        	log.info("No user found");
        	return Response.status(200).entity("NULL_USER").build();        	
        }
        else{
		boolean loginSuccess = authenticateUser(password,user.getPassword());
		if(!loginSuccess){
			log.info("Invalid Password");
			return Response.status(200).entity("INVALID_PASSWORD").build();
			
		}
		else{
			log.info("User found");
			return Response.status(200).entity(ServiceUtil.getUser4Edit(user)).build();
		}
        } 
	}	
	
	private User getUserById(long id){
		return userManager.getUserById(id);	
	}
	
	private boolean hasUniqueEmail(String email, User user){		
		
		if(Validator.isNull(user)){
			return true;
		}
		else {
			if(user.getEmail().equalsIgnoreCase(email)){
			return true;	
			}
			else{
				return false;
			}
				}
	}
	
	private boolean hasUniqueEmail(String email){		
		User user = userManager.getUserByEmail(email);
		if(Validator.isNull(user)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean validatePassword(String passwordFromDb, String passwordEntered, String newPassword, String newPasswordConfirm,User user){
		
		//This is to prevent null hash value from being saved
		user.setPassword(passwordFromDb);
		//e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855 is the hashvalue for null
			if((!passwordEntered.equals(AppConstants.NULL_HASH_VALUE))
				&& (!newPassword.equals(AppConstants.NULL_HASH_VALUE))) {
				if(newPassword.equals(newPasswordConfirm)){
				if(passwordFromDb.equals(passwordEntered)){
				user.setPassword(newPassword);
				return true;
				}
				else{
					response=response+"Invalid password. ";
					return false;
				}
				}
				else{
					response=response+"New Password and Confirm Password did not match. ";
					return false;
				}
			}
			else if(passwordEntered.equals(AppConstants.NULL_HASH_VALUE)
				&& newPassword.equals(AppConstants.NULL_HASH_VALUE)
				&& newPasswordConfirm.equals(AppConstants.NULL_HASH_VALUE)){
				return true;
			}
			else{
				response=response+"Error changing your password. ";
				return false;
			}
					
	}	
	
	private boolean validateUser(User user){
		boolean valid = true;
		response="";
		if(Validator.isNull(user.getFirstName())){
			log.debug("First Name cannot be emtpy.\n");
			response="First Name is required. ";
			valid = false;			
		}
		if(Validator.isNull(user.getLastName())){
			log.debug("Last Name cannot be emtpy.\n");
			response=response+"Last Name is required. ";
			valid = false;
		}
		if(user.getPassword().equals(AppConstants.NULL_HASH_VALUE) && Validator.isNull(user.getNewPassword())){
			log.debug("Password cannot be empty.\n");
			response=response+"Password is required. ";
			valid = false;
		}
		
		if(Validator.isNull(user.getEmail())){
			log.debug("Email is required.\n");
			response=response+"Email is required. ";
			valid = false;	
		}
		else if(!Validator.isEmailAddress(user.getEmail())){
			log.debug("Invalid Email.\n");
			response=response+"Invalid Email. ";
			valid = false;
		}
		
		if (Validator.isNotNull(user.getPhone()) && !Validator.isPhoneNumber(user.getPhone())){
			log.debug("Invalid Phone.\n");
			response=response+"Invalid Phone. ";
			valid = false;
		}
		return valid;		
	}
	public static boolean authenticateUser(String passwordSent, String passwordInDb){
		return passwordSent.equals(passwordInDb)?true:false;
	}	
		
	private static Log log = LogFactoryUtil.getLog(RESTService.class);
}