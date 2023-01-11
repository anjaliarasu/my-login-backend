package com.springbootcrud.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springbootcrud.model.Patients;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Class Doctors */
@Entity(name="doctors")
public class Doctors {


	public Doctors() {
		super();
	}

	/** The id. */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private int id;
	
	/** The name. */

	@Column(name = "NAME", nullable = false, length = 100)
	private String name;
	
	/** The email address. */
	@Column(name = "EMAIL_ADDRESS", nullable = false, length = 150)
	private String emailAddress;

	/** The phone number. */
	@Column(name = "PHONE_NUMBER", length = 200)
	@Pattern(regexp = "^(0|[0-9][0-9]*)$")
	@Size(min = 10, max = 10, message = "Length should be max 15")
	private String phoneNumber;
	
	@Column(name = "CITY", nullable = false, length = 150)
	private String city;
	
	@NotNull(message="Department cannot be null")
	@Column(name = "DEPT", length = 150)
	private String department;
	
	@JsonIgnore
	@JsonIgnoreProperties("doctor")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
	private Set<Patients> patients;


	public Doctors(int id, String name, String emailAddress,
			@Pattern(regexp = "^(0|[0-9][0-9]*)$") @Size(min = 10, max = 10, message = "Length should be max 15") String phoneNumber,
			String city, @NotNull(message = "Department cannot be null") String department, Set<Patients> patients) {
		super();
		this.id = id;
		this.name = name;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.department = department;
		this.patients = patients;
	}
	public Doctors(int i) {
		this.id=i;
	}
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Patients> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patients> patients) {
		this.patients = patients;
	}
	
	
	
	
}
