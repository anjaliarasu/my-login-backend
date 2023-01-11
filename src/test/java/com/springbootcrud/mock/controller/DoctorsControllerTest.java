package com.springbootcrud.mock.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.reflect.TypeToken;
import com.springbootcrud.TestUtils;
import com.springbootcrud.business.impl.DoctorsBusinessImpl;
import com.springbootcrud.controller.DoctorsController;
import com.springbootcrud.model.Doctors;
import java.util.NoSuchElementException;

/**
 * 
 * @author Anjali T
 * 
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(DoctorsController.class)
@AutoConfigureMockMvc(addFilters = false)

public class DoctorsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	DoctorsBusinessImpl docService;

	private final String URL = "/doctors";

	@Test
	public void testAddDoctor() throws Exception {

		// prepare data and mock's behaviour
		Doctors docStub = new Doctors(10, "Anand", "anand@gmail.com", "9876542347", "Bhopal", "Neuro", null);
		when(docService.saveOrUpdate(any(Doctors.class))).thenReturn(docStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(docStub))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

		// verify that service method was called once
		verify(docService).saveOrUpdate(any(Doctors.class));

		Doctors resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Doctors.class);
		assertNotNull(resultEmployee);
		assertEquals(10, resultEmployee.getId());

	}

	@Test
	public void testGetDoctor() throws Exception {

		// prepare data and mock's behaviour
		Doctors docStub = new Doctors(10, "Anand", "anand@gmail.com", "9876542347", "Bhopal", "Neuro", null);
		when(docService.getDoctorsById(any(Integer.class))).thenReturn(docStub);

		// execute
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get(URL + "/{id}", new Integer(10)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(docService).getDoctorsById(any(Integer.class));

		Doctors resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), Doctors.class);
		assertNotNull(resultEmployee);
		assertEquals(10, resultEmployee.getId());
	}

	@Test
	public void testGetDoctorNotExist() throws Exception {

		// prepare data and mock's behaviour // Not Required as employee Not Exist
		// scenario
		Doctors docStub = new Doctors(13);

		when(docService.getDoctorsById(any(Integer.class))).thenThrow(new NoSuchElementException("not found"));

		// execute
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get(URL + "/{id}", new Integer(10)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status);

		// verify that service method was called once
		verify(docService).getDoctorsById(any(Integer.class));

		/*
		 * Doctors resultEmployee
		 * =TestUtils.jsonToObject(result.getResponse().getContentAsString(),Doctors.
		 * class); assertNull(resultEmployee);
		 */
	}

	@Test
	public void testGetAllDoctors() throws Exception {

		// prepare data and mock's behaviour
		List<Doctors> docList = buildDoctors();
		when(docService.getAllDoctors()).thenReturn(docList);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(docService).getAllDoctors();

		// get the List<Employee> from the Json response
		TypeToken<List<Doctors>> token = new TypeToken<List<Doctors>>() {
		};

		@SuppressWarnings("unchecked")
		List<Doctors> docListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("Employees not found", docListResult);
		assertEquals("Incorrect Employee List", docList.size(), docListResult.size());

	}

	@Test
	public void testDeleteDoctor() throws Exception {
		// prepare data and
		// mock's behaviour
		Doctors docStub = new Doctors(10);
		when(docService.getDoctorsById(any(Integer.class))).thenReturn(docStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", new Integer(10))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

		// verify that service method was called once
		verify(docService).delete(any(Integer.class));

	}

	@Test
	public void testUpdateDoctor() throws Exception { // prepare data and
		// mock's behaviour // here the stub is the updated employee object with ID
		// equal to ID of // employee need to be updated
		Doctors docStub = new Doctors(10, "Anand", "anand@gmail.com", "9876542347", "Bhopal", "Neuro", null);
		when(docService.getDoctorsById(any(Integer.class))).thenReturn(docStub);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(docStub))).andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(docService).update(any(Doctors.class), any(Integer.class));

	}

	private List<Doctors> buildDoctors() {
		Doctors e1 = new Doctors(10, "Anand", "anand@gmail.com", "9876542347", "Bhopal", "Neuro", null);
		Doctors e2 = new Doctors(11, "Jothi", "Jothi@gmail.com", "9654132673", "Trivandrum", "Optometry", null);
		List<Doctors> docList = Arrays.asList(e1, e2);
		return docList;
	}

}