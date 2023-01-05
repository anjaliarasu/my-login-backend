package com.springbootcrud.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootcrud.business.DoctorsBusiness;
import com.springbootcrud.business.PatientsBusiness;
import com.springbootcrud.model.Doctors;
import com.springbootcrud.model.Patients;
import com.springbootcrud.repository.DoctorRepository;
import com.springbootcrud.repository.PatientsRepository;

@Service("patientsBusiness")
public class PatientsBusinessImpl implements PatientsBusiness {

	@Autowired  
	PatientsRepository patientsRepository;  
	@Override
	public List<Patients> getAllPatients() {
		List<Patients> patients = new ArrayList<Patients>();  
		patientsRepository.findAll().forEach(pats -> patients.add(pats));  
		return patients;  
	}

	@Override
	public Patients getPatientsById(int id) {
		return patientsRepository.findById(id).get();
	}

	@Override
	public void saveOrUpdate(Patients patients) {
		patientsRepository.save(patients);
	}

	@Override
	public void delete(int id) {
		patientsRepository.deleteById(id);
	}

	@Override
	public void update(Patients patients, int patientid) {
		patientsRepository.save(patients);
		
	}

}
