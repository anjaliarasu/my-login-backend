package com.myloginbackend.mock.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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

import com.myloginbackend.business.impl.JwtService;
import com.myloginbackend.business.impl.UserServiceImpl;
import com.myloginbackend.config.JwtAuthenticationEntityPoint;
import com.myloginbackend.config.JwtRequestFilter;
import com.myloginbackend.controller.AuthController;
import com.myloginbackend.model.wrapper.JwtRequest;
import com.myloginbackend.util.JwtUtil;
import com.myloginbackend.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class, includeFilters = {
		// to include JwtUtil in spring context
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { JwtUtil.class,
				JwtAuthenticationEntityPoint.class }) })
public class AuthControllerTest {

	@MockBean
	private UserServiceImpl userCommandService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtService userDetailsServiceImpl;

	@MockBean
	private JwtUtil jwtUtil;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@MockBean
	AuthenticationManager authenticationManager;

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

	
	  /////
	  
	  @Test public void testValidLogin() throws Exception { 
		  
		  JwtRequest req=new JwtRequest("anjali@gmail.com", "1234@Anjali!"); 
		  MvcResult result =
	  mockMvc .perform(MockMvcRequestBuilders.post("/api/signin")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(TestUtils.objectToJson(req)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
	  
	  logger.info("Resssulllttt"+result.getResponse().getContentAsString());
	  
		
	  } /////
	  

	@Test
	public void testSignupWithoutUserFirstName() throws Exception {

		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
		Date date = DateFor.parse("1995-05-28");
		System.out.println("Date : " + date);

		com.myloginbackend.model.User user = new com.myloginbackend.model.User("", "", "anjali123", "anjali@gmail.com",
				"9876543212", date, null);
		RequestBuilder request = MockMvcRequestBuilders.post("/api/signup")
                .header("Authorization", "Bearer " + jwtToken)
.content(TestUtils.objectToJson(user))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		when(userDetailsServiceImpl.loadUserByUsername(("foo@email.com"))).thenReturn(dummy);

		MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn();

		logger.info(mvcResult.getResponse().getContentAsString());
		int status = mvcResult.getResponse().getStatus();
		/*
		 * JwtRequest req=new JwtRequest("anjali@gmail.com", "anjali123"); mockMvc
		 * .perform(MockMvcRequestBuilders.post("/api/signin")
		 * .contentType(MediaType.APPLICATION_JSON_UTF8)
		 * .content(TestUtils.objectToJson(req)).accept(MediaType.APPLICATION_JSON_UTF8)
		 * ).andExpect(status().isOk()).andReturn();
		 */
	}
}