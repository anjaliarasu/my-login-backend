package com.springbootcrud.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootcrud.model.Role;
import com.springbootcrud.repository.RoleRepository;


public interface RoleService {
	
	
	public Role createNewRole(Role role);
}
