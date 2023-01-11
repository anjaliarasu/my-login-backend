package com.springbootcrud.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	/** The logger. */
	private Logger logger = LogManager.getLogger(PatientsController.class.getName());

	@Autowired
	PatientsBusiness patientService;

	// creating a get mapping that retrieves all the patients detail from the
	// database
	@GetMapping("/patients")
	public ResponseEntity<List<Patients>> getAllPatients() {
		logger.info("Getting all patients");
		return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
	}

	// creating a get mapping that retrieves the detail of a specific doctor
	@GetMapping("/patients/{doctorid}")
	public ResponseEntity<Patients> getPatients(@Pattern(regexp = "^[0-9]*$") @PathVariable("patientid") int patientid) {
		Patients patient;
		logger.info("Getting patients with id:" + patientid);
		patient = patientService.getPatientsById(patientid);
		//if(patient!=null)
			return new ResponseEntity<>(patient, HttpStatus.OK);
			/*
			 * else { logger.info("Patient with id " + patientid+" not found"); return new
			 * ResponseEntity<>(patient, HttpStatus.NOT_FOUND); }
			 */
	}

	// creating a delete mapping that deletes a specified doctor
	@DeleteMapping("/patients/{patientid}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Patients> deletePatient(@Pattern(regexp = "^[0-9]*$") @PathVariable("patient") int patient) {
		logger.info("Deleting patients with id:" + patient);
		patientService.delete(patient);
		return new ResponseEntity<>(HttpStatus.GONE);

	}

	// creating post mapping that post the doctor detail in the database
	@PostMapping("/patients")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Patients> savePatients(@Valid @RequestBody Patients patients) {
		logger.info("Saving new patient");

		Patients patient = patientService.saveOrUpdate(patients);

		//if(patient!=null)
		logger.info("Saving new patient successful");

		return new ResponseEntity<>(patient, HttpStatus.CREATED);

	}

	// creating put mapping that updates the doctor detail
	@PutMapping("/patients")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Patients> update(@Valid @RequestBody Patients patients) {
		logger.info("Updating existing patient");

		Patients patient = patientService.saveOrUpdate(patients);
		//if(patient!=null)
		logger.info("Updating existing doctor successful");

		return new ResponseEntity<>(patient, HttpStatus.OK);
	}
}
