package com.myloginbackend.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myloginbackend.model.Role;


@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
	
	
}
