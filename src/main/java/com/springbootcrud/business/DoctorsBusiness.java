package com.springbootcrud.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springbootcrud.model.Doctors;
import com.springbootcrud.repository.DoctorRepository;

public interface DoctorsBusiness {

	//getting all books record by using the method findaAll() of CrudRepository  
	public List<Doctors> getAllDoctors();   
	
	//getting a specific record by using the method findById() of CrudRepository  
	public Doctors getDoctorsById(int id)  ; 
	
	//saving a specific record by using the method save() of CrudRepository  
	public Doctors saveOrUpdate(Doctors doctors)   ;
	 
	//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(int id)   ;
	
	//updating a record  
	public void update(Doctors doctors, int doctorid)   ;
	 
}
