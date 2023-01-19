package com.myloginbackend.mock.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import com.myloginbackend.business.UserService;
import com.myloginbackend.business.impl.JwtService;
import com.myloginbackend.business.impl.UserServiceImpl;
import com.myloginbackend.config.JwtAuthenticationEntityPoint;
import com.myloginbackend.config.JwtRequestFilter;
import com.myloginbackend.controller.AuthController;
import com.myloginbackend.model.wrapper.JwtRequest;
import com.myloginbackend.model.wrapper.JwtResponse;
import com.myloginbackend.repository.UserRepository;
import com.myloginbackend.util.JwtUtil;
import com.myloginbackend.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class, includeFilters = {
		// to include JwtUtil in spring context
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { JwtUtil.class,
				JwtAuthenticationEntityPoint.class }) })
public class AuthControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private JwtService jwtService;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtUtil jwtUtil;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	private UserRepository userRepo;
	
	
	private static UserDetails dummy;
	private static String jwtToken;

	Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

	@BeforeEach
	public void setUp() {
		dummy = new User("anjali@email.com", "1234@Anjali!", new ArrayList<>());
		jwtToken = jwtUtil.generateToken(dummy);
	}

	private String createAuthenticationBody(String username, String passwordHash) {
		return "username=" + URLEncoder.encode(username) + "&password=" + URLEncoder.encode(passwordHash);
	}

	
	  
	  @Test public void testValidLogin() throws Exception { 
		  
		   SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
			Date date = DateFor.parse("1995-05-28");
			System.out.println("Date : " + date);

		  com.myloginbackend.model.User userstub = new com.myloginbackend.model.User("anjali", "t", "anjali123", "anjali@gmail.com",
					"9876543212", date, null);
		
		  when(jwtService.loadUserByUsername(Mockito.any(String.class))).thenReturn(dummy);
		  
		  JwtRequest req=new JwtRequest(dummy.getUsername(), dummy.getPassword()); 
		  JwtResponse resStub=new JwtResponse(userstub,"authToken");
				
		  when(jwtService.createJwtToken(Mockito.any(JwtRequest.class))).thenReturn(resStub);
			
		  MvcResult result =
	  mockMvc .perform(MockMvcRequestBuilders.post("/api/signin")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(TestUtils.objectToJson(req)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	  
		  int status = result.getResponse().getStatus();
			assertEquals("Incorrect Response status", 200, status);

	  logger.info("Resssulllttt"+result.getResponse().getContentAsString());
	  
	  JwtResponse response=TestUtils.jsonToObject(result.getResponse().getContentAsString(), JwtResponse.class);
	  assertEquals("Incorrect result", "authToken",response.getJwtToken());

	  } 
	  
		@Test
		public void testSignupWithValidUser() throws Exception {

			SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
			Date date = DateFor.parse("1995-05-28");
			System.out.println("Date : " + date);

			com.myloginbackend.model.User user = new com.myloginbackend.model.User("anjali", "t", "anjali123", "anjali@gmail.com",
					"9876543212", date, null);
			when(userService.registerNewUser(Mockito.any(com.myloginbackend.model.User.class))).thenReturn(user);
			RequestBuilder request = MockMvcRequestBuilders.post("/api/signup")
	                .content(TestUtils.objectToJson(user))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			
			MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();

			logger.info(mvcResult.getResponse().getContentAsString());

			// verify
			int status = mvcResult.getResponse().getStatus();
			assertEquals("Incorrect Response", HttpStatus.CREATED.value(), status);

			// verify that service method was called once
			verify(userService).registerNewUser(any(com.myloginbackend.model.User.class));

			com.myloginbackend.model.User response=TestUtils.jsonToObject(mvcResult.getResponse().getContentAsString(), com.myloginbackend.model.User.class);
			assertEquals("anjali@gmail.com", response.getUserEmail());
		}
		
	@Test
	public void testSignupWithoutUserFirstName() throws Exception {

		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
		Date date = DateFor.parse("1995-05-28");
		System.out.println("Date : " + date);

		com.myloginbackend.model.User user = new com.myloginbackend.model.User("", "", "anjali123", "anjali@gmail.com",
				"9876543212", date, null);
		when(userService.registerNewUser(Mockito.any(com.myloginbackend.model.User.class))).thenThrow(new DataIntegrityViolationException("Invalid Username"));
				
		RequestBuilder request = MockMvcRequestBuilders.post("/api/signup")
                .header("Authorization", "Bearer " + jwtToken)
                .content(TestUtils.objectToJson(user))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isNotAcceptable()).andReturn();

		logger.info(mvcResult.getResponse().getContentAsString());

		// verify
		int status = mvcResult.getResponse().getStatus();
		assertEquals("Incorrect Response", HttpStatus.NOT_ACCEPTABLE.value(), status);

		// verify that service method was called once
		verify(userService).registerNewUser(any(com.myloginbackend.model.User.class));

		//assertNull(userResult);
	}
}