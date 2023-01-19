package com.myloginbackend.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.JoinColumn;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID",nullable = false)
	private int id;
	
	@Column(name="FIRST_NAME",nullable = false)
	@Size(min=5,max = 25,message = "First name should be atleast 5 to 25 characters")
	private String userFirstName;
	
	@Column(name="LAST_NAME",nullable = false)
	@Size(min=1,max = 25,message = "First name should be atleast 1 to 25 characters")
	private String userLastName;
	
	@Column(name="PASSWORD",nullable = false)
	@Pattern(regexp="^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$",message="Password should contain atleast 1 uppercase,1 lowercase and 1 other characters")
	@Size(min=8,max = 15,message = "Password should be of length 8 to 15")
	private String userPassword;
	
	@Column(name = "EMAIL_ADDRESS",unique=true, nullable = false, length = 50)
	@Size(max = 50)
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
	private String userEmail;

	/** The phone number. */
	@Column(name = "PHONE_NUMBER",unique=true, length = 200)
	@Pattern(regexp = "^(0|[0-9][0-9]*)$")
	@Size(min = 10, max = 15, message = "Number of digits in phone number should be max 15")
	private String phoneNumber;
	
	/** The dob. */
	@Column(name = "DOB")
	private Date dob;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", 
			joinColumns = {
					@JoinColumn(name = "user_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "role_id")
			}
	)
	private Set<Role> roles;

	

	
	public User(int id, @Size(max = 20) String userFirstName, @Size(max = 20) String userLastName,
			@Size(max = 120) String userPassword,
			@Size(max = 50) @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") String userEmail,
			@Pattern(regexp = "^(0|[0-9][0-9]*)$") @Size(min = 10, max = 15, message = "Number of digits in phone number should be max 15") String phoneNumber,
			Date dob, Set<Role> roles) {
		super();
		this.id = id;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.roles = roles;
	}


	public User(@Size(max = 20) String userFirstName, @Size(max = 20) String userLastName,
			@Size(max = 120) String userPassword,
			@Size(max = 50) @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$") String userEmail,
			@Pattern(regexp = "^(0|[0-9][0-9]*)$") @Size(min = 10, max = 15, message = "Number of digits in phone number should be max 15") String phoneNumber,
			Date dob, Set<Role> roles) {
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.roles = roles;
	}


	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String string, String string2, Object object) {
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
