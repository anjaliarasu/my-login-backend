package com.myloginbackend.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myloginbackend.model.Role;
import com.myloginbackend.model.User;
import com.myloginbackend.repository.RoleRepository;
import com.myloginbackend.repository.UserRepository;

public interface UserService {
	

	public User registerNewUser(User user);
	
	public void initRolesAndUser();
	
	public String getEncodedPassword(String password);
}