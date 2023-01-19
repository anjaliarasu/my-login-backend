package com.myloginbackend.business.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myloginbackend.business.UserService;
import com.myloginbackend.model.Role;
import com.myloginbackend.model.User;
import com.myloginbackend.repository.RoleRepository;
import com.myloginbackend.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		Role role = roleRepo.findById("User").get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		userRepo.findByUserEmail(user.getUserEmail()).ifPresent(e->{throw new DataIntegrityViolationException("Email ID already registered!,Do you want to login?");});
		userRepo.findByPhoneNumber(user.getPhoneNumber()).ifPresent(e->{throw new DataIntegrityViolationException("Phone number already registered!,Do you want to login?");});
		if(user.getUserFirstName()==null || user.getUserFirstName().length()<8 || user.getUserFirstName().length()>25)
			throw new DataIntegrityViolationException("Invalid Username");
		return userRepo.save(user);
	}
	
	public void initRolesAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Admin role");
		roleRepo.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Default role for newly created record");
		roleRepo.save(userRole);
		
		/*
		 * User adminUser = new User(); adminUser.setUserEmail("admin123@gmail.com");
		 * adminUser.setUserFirstName("admin"); adminUser.setUserLastName("admin");
		 * adminUser.setUserPassword(getEncodedPassword("admin@12345")); Set<Role>
		 * adminRoles = new HashSet<>(); adminRoles.add(adminRole);
		 * adminUser.setRoles(adminRoles); userRepo.save(adminUser);
		 */
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}