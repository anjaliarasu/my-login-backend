package com.myloginbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myloginbackend.business.UserService;
import com.myloginbackend.business.impl.*;
import com.myloginbackend.model.User;
import com.myloginbackend.model.wrapper.JwtRequest;
import com.myloginbackend.model.wrapper.JwtResponse;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signin")
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}
	@PostMapping("/signup")
	public ResponseEntity<User> registerNewUser(@RequestBody User user) {
		User userResp=userService.registerNewUser(user);
		if(userResp!=null)
			return new ResponseEntity<>(userService.registerNewUser(userResp), HttpStatus.CREATED);
		else
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	
	}
	
}