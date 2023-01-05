package com.springbootcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootcrud.business.PatientsBusiness;
import com.springbootcrud.model.Patients;

@RestController
public class PatientsController {

	@Autowired
	PatientsBusiness patientservice;
	//creating a get mapping that retrieves all the Patients detail from the database   
	@GetMapping("/patients")  
	private List<Patients> getAllPatients()   
	{  
	return patientservice.getAllPatients();  
	}  
	//creating a get mapping that retrieves the detail of a specific patient  
	@GetMapping("/patients/{patientid}")  
	private Patients getBooks(@PathVariable("patientid") int patientid)   
	{  
	return patientservice.getPatientsById(patientid); 
	}  
	//creating a delete mapping that deletes a specified patient  
	@DeleteMapping("/patients/{patientid}")  
	private void deleteBook(@PathVariable("patientid") int patientid)   
	{  
		patientservice.delete(patientid);  
	}  
	//creating post mapping that post the patient detail in the database  
	@PostMapping("/patients")  
	private int saveBook(@RequestBody Patients patients)   
	{  
		patientservice.saveOrUpdate(patients);  
	return patients.getId();
	}  
	//creating put mapping that updates the patient detail   
	@PutMapping("/patients")  
	private Patients update(@RequestBody Patients patients)   
	{  
		patientservice.saveOrUpdate(patients);  
	return patients;  
	}  
}
