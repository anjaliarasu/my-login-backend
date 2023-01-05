package com.springbootcrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootcrud.model.Doctors;

public interface DoctorRepository extends JpaRepository<Doctors,Integer> {

	Optional<Doctors> findByEmailAddress(String emailAddress);

	Optional<Doctors> findByPhoneNumber(String phoneNumber);
	

}
