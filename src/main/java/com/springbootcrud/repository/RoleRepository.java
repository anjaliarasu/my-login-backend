package com.springbootcrud.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootcrud.model.Role;


@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
	
	
}
