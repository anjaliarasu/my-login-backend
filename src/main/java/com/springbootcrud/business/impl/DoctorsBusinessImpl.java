package com.springbootcrud.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.springbootcrud.business.DoctorsBusiness;
import com.springbootcrud.model.Doctors;
import com.springbootcrud.repository.DoctorRepository;

@Service("doctorsBusiness")
public class DoctorsBusinessImpl implements DoctorsBusiness {

	@Autowired  
	DoctorRepository doctorsRepository;  
	@Override
	public List<Doctors> getAllDoctors() {
		List<Doctors> doctors = new ArrayList<Doctors>();  
		doctorsRepository.findAll().forEach(docs -> doctors.add(docs));  
		return doctors;  
	}

	@Override
	public Doctors getDoctorsById(int id) {
		Doctors doctor= doctorsRepository.findById(id).orElseThrow(()->
		new NoSuchElementException("Doctor with id "+id+" not found"));
		return doctor;
	}

	@Override
	public Doctors saveOrUpdate(Doctors doctors) {
		doctorsRepository.findById(doctors.getId()).ifPresent(o->{throw new DataIntegrityViolationException("Doctor "
				+ "with id "+doctors.getId()+" already present");});
		doctorsRepository.findByEmailAddress(doctors.getEmailAddress()).ifPresent(o->{throw new DataIntegrityViolationException("Doctor "
				+ "with mail address "+doctors.getEmailAddress()+" already registered");});
		doctorsRepository.findByPhoneNumber(doctors.getPhoneNumber()).ifPresent(o->{throw new DataIntegrityViolationException("Doctor "
				+ "with phone number "+doctors.getPhoneNumber()+" already present");});
	
		return doctorsRepository.save(doctors);
	}

	@Override
	public void delete(int id) {
		Doctors doctor= doctorsRepository.findById(id).orElseThrow(()->
		new NoSuchElementException("Doctor with id "+id+" not found"));
		doctorsRepository.delete(doctor);
	}

	@Override
	public Doctors update(Doctors doctors, int doctorid) {
		Doctors doctor= doctorsRepository.findById(doctorid).orElseThrow(()->
		new NoSuchElementException("Doctor with id "+doctorid+" not found"));
		
		doctor.setName(doctors.getName());
		doctor.setEmailAddress(doctors.getEmailAddress());
		doctor.setPhoneNumber(doctors.getPhoneNumber());
		doctor.setDepartment(doctors.getDepartment());
		doctor.setCity(doctors.getCity());
		doctor.setPatients(doctors.getPatients());
		return doctorsRepository.save(doctor);
		
	}

}
