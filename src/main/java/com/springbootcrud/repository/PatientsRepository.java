package com.springbootcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootcrud.model.Patients;

public interface PatientsRepository extends JpaRepository<Patients,Integer> {

}
