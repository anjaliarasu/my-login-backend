package com.springbootcrud.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootcrud.business.RoleService;
import com.springbootcrud.model.Role;
import com.springbootcrud.repository.RoleRepository;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepo;
	
	public Role createNewRole(Role role) {
		return roleRepo.save(role);
	}
}
