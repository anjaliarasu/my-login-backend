package com.springbootcrud.model;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Class Patients */

@Entity(name="patients")
public class Patients {

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
	@Column(name = "PHONE_NUMBER", length = 10)
	@Pattern(regexp = "^(0|[0-9][0-9]*)$")
	@Size(min = 10, max = 10, message = "Length should be max 15")
	private String phoneNumber;
	
	@Column(name = "CITY", nullable = false, length = 150)
	private String city;
	
	@Column(name = "TYPE", nullable = false, length = 150)
	private String type;
	
	@JsonIgnore
	@JsonIgnoreProperties("doctor")
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name = "DOCTOR_ID", nullable = false)
	private Doctors doctor;

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

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
