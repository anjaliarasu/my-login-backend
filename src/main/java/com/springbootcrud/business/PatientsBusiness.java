package com.springbootcrud.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springbootcrud.model.Doctors;
import com.springbootcrud.model.Patients;
import com.springbootcrud.repository.DoctorRepository;

public interface PatientsBusiness {

	//getting all books record by using the method findaAll() of CrudRepository  
	public List<Patients> getAllPatients();   
	
	//getting a specific record by using the method findById() of CrudRepository  
	public Patients getPatientsById(int id)  ; 
	
	//saving a specific record by using the method save() of CrudRepository  
	public void saveOrUpdate(Patients patients)   ;
	 
	//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(int id)   ;
	
	//updating a record  
	public void update(Patients patients, int patientid)   ;
	 
}
