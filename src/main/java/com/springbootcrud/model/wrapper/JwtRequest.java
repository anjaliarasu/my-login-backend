package com.springbootcrud.model.wrapper;

public class JwtRequest {
	
	private String userName;
	
	public JwtRequest(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}