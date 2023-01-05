package com.springbootcrud.controller;

import java.util.List;

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

import com.springbootcrud.business.DoctorsBusiness;
import com.springbootcrud.framework.customexception.ControllerExceptionHandler;
import com.springbootcrud.model.Doctors;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
public class DoctorsController {

	/** The logger. */
	private Logger logger = LogManager.getLogger(DoctorsController.class.getName());

	@Autowired
	DoctorsBusiness doctorService;

	// creating a get mapping that retrieves all the doctors detail from the
	// database
	@GetMapping("/doctors")
	public ResponseEntity<List<Doctors>> getAllDoctors() {
		logger.info("Getting all doctors");
		return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
	}

	// creating a get mapping that retrieves the detail of a specific doctor
	@GetMapping("/doctors/{doctorid}")
	public ResponseEntity<Doctors> getDoctors(@Pattern(regexp = "^[0-9]*$") @PathVariable("doctorid") int doctorid) {
		Doctors doctor;
		logger.info("Getting doctors with id:" + doctorid);
		doctor = doctorService.getDoctorsById(doctorid);
		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}

	// creating a delete mapping that deletes a specified doctor
	@DeleteMapping("/doctors/{doctorid}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Doctors> deleteDoctor(@Pattern(regexp = "^[0-9]*$") @PathVariable("doctorid") int doctorid) {
		logger.info("Deleting doctors with id:" + doctorid);
		doctorService.delete(doctorid);
		logger.info("Deleting doctors with id:" + doctorid + " successful");
		return new ResponseEntity<>(HttpStatus.OK);

	}

	// creating post mapping that post the doctor detail in the database
	@PostMapping("/doctors")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Doctors> saveDoctors(@Valid @RequestBody Doctors doctors) {
		logger.info("Saving new doctor");

		Doctors doctor = doctorService.saveOrUpdate(doctors);

		logger.info("Saving new doctor successful");

		return new ResponseEntity<>(doctor, HttpStatus.OK);

	}

	// creating put mapping that updates the doctor detail
	@PutMapping("/doctors")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<Doctors> update(@Valid @RequestBody Doctors doctors) {
		logger.info("Updating existing doctor");

		Doctors doctor = doctorService.saveOrUpdate(doctors);
		logger.info("Updating existing doctor successful");

		return new ResponseEntity<>(doctor, HttpStatus.OK);
	}
}
