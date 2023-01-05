package com.springbootcrud.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springbootcrud.model.Role;
import com.springbootcrud.model.User;
import com.springbootcrud.repository.RoleRepository;
import com.springbootcrud.repository.UserRepository;

public interface UserService {
	

	public User registerNewUser(User user);
	
	public void initRolesAndUser();
	
	public String getEncodedPassword(String password);
}