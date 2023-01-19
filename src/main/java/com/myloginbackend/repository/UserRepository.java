package com.myloginbackend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myloginbackend.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByUserEmail(String username);
	
	Optional<User> findByPhoneNumber(String phone);

}