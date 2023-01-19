package com.myloginbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myloginbackend.model.Role;
import com.myloginbackend.repository.RoleRepository;


public interface RoleService {
	
	
	public Role createNewRole(Role role);
}
