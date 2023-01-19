package com.myloginbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myloginbackend.business.RoleService;
import com.myloginbackend.model.Role;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/roles")
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
}