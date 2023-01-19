package com.myloginbackend.business.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myloginbackend.model.User;
import com.myloginbackend.model.wrapper.JwtRequest;
import com.myloginbackend.model.wrapper.JwtResponse;
import com.myloginbackend.repository.UserRepository;
import com.myloginbackend.util.JwtUtil;


@Service
public class JwtService implements UserDetailsService{

	/** The logger. */
	private Logger logger = LogManager.getLogger(JwtService.class.getName());

	@Autowired
	private UserRepository userReop;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		final UserDetails userDetaisl = loadUserByUsername(userName);
		String newGeneratedToken = jwtUtil.generateToken(userDetaisl);
		User user = userReop.findByUserEmail(userName).get();
		return new JwtResponse(user, newGeneratedToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Checking for username in database");
		User user = userReop.findByUserEmail(username).orElseThrow(()->new NoSuchElementException("Username not registered! Please signup!"));
		//if (user != null) {
			logger.info("User with username-"+username+" found");
			return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getUserPassword(), getAuthorities(user));
			/*
			 * } else { throw new UsernameNotFoundException("Username is not valid"); }
			 */
	}
	
	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		logger.info("Setting User roles for the user:"+user.getUserFirstName());
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		return authorities;
	}
	
	private void authenticate(String userName, String userPassword) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		}
		catch(UsernameNotFoundException e) {
			logger.error("Username is not valid");
			throw new Exception("User does not exist! Please Register!");
		}
		catch (DisabledException e) {
			logger.error("User is disabled");
			throw new Exception("User is disabled");
		} catch(BadCredentialsException e) {
			logger.error("Bad credentials from user");
			throw new Exception("Bad credentials from user");
		}
		
	}
	
}

