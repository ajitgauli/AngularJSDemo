package com.example.www.model;

import java.io.Serializable;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name ="subscriber",uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	public User(){
		
	}
	
	public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private long id;	

	@Column
	@NotNull
	private String firstName;

	@Column
	@NotNull
	private String lastName;
	
	@Column(unique=true)
	@Email
	@NotNull
	private String email;
	
	@Column
	@Pattern(regexp="^\\(?(\\d{3})\\)?[ .-]?(\\d{3})[ .-]?(\\d{4})$")
	private String phone;
	
	@Column
	@Min(3)
	@NotNull	
	private String password;
	
	@Transient	
	private String newPassword;
	
	@Transient	
	private String newPasswordConfirm;
    

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}
	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}
	public String getFirstName() {

		return this.firstName;
	}
	
	public String getLastName() {

		return this.lastName;
	}	
	
	public String getEmail() {

		return this.email;
	}
	
	
	public String getPhone() {

		return this.phone;
	}
	
	public String getPassword() {

		return this.password;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + "]";
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return 13 * email.hashCode();
    }

}
