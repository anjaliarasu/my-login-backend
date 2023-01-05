package com.springbootcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootcrud.business.RoleService;
import com.springbootcrud.model.Role;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
}