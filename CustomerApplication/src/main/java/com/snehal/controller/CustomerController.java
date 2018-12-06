package com.snehal.controller;


import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.snehal.customerservice.CustomerDataService;
import com.snehal.customerservice.SessionValidationService;
import com.snehal.entity.model.Customer;
import com.snehal.entity.model.Message;
import com.snehal.entity.model.Token;
import com.snehal.entity.model.User;


/**
 * @author $nehal
 *
 */
@RestController
public class CustomerController {
	
	static Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	SessionValidationService sessionValidationService;
	
	@Autowired
	CustomerDataService customerDataService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String welcome(){
		
		return "Welcome to the Customer Service";
	}
	
	@RequestMapping(value="/customer/{id}", produces="application/json", method=RequestMethod.GET)
	public ResponseEntity<Object> getCustomer(@PathVariable("id") int id,
			@RequestHeader(value="Authorization", required=true) String authToken){
		try {
			if(sessionValidationService.validateUser(authToken)){
			
				Customer customer = customerDataService.getCustomer(id);
				
				if(customer==null){
					log.debug("customer not found with id " + id);
					return new ResponseEntity<Object>( new Message("Customer not found"), HttpStatus.NOT_FOUND);
					
				}else{
					log.debug("Cutomer details retrieved.");
					return new ResponseEntity<Object>(customer, HttpStatus.FOUND );
				}
			}else{
				return new ResponseEntity<Object>( new Message("User Authentication failed."), HttpStatus.UNAUTHORIZED);
			} 
		}catch (ServletException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Object>( new Message("Could not get details"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value="/customer", consumes="application/json", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<Object> addCustomer(@RequestBody Customer customer,
			@RequestHeader(value="Authorization", required=true) String authToken){
		
		try {
			if(sessionValidationService.validateUser(authToken)){
				
				int id = customerDataService.saveCustomer(customer);
				log.debug("customer created successfully. id : " + id);
				return new ResponseEntity<Object>(new Message(id+"") , HttpStatus.CREATED);
				
			}else{
				return new ResponseEntity<Object>(new Message("User Authentication failed.") , HttpStatus.UNAUTHORIZED);
			}
		} catch (ServletException e) {
			
			log.error(e.getMessage());
			return new ResponseEntity<Object>(new Message("Failed to add Customer") , HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public ResponseEntity<Object> login(@RequestBody User user){
		
		try {
			if(customerDataService.validateUser(user.getUser(), user.getPassword())){
			
				String token = "ab#r%h$mn" + user.getUser();
				
				sessionValidationService.registerUserSession(user.getUser(), token);
				return new ResponseEntity<Object>(new Token(token), HttpStatus.OK);
			}else{
				
				return new ResponseEntity<Object>(new Message("Wrong credentials"), HttpStatus.BAD_REQUEST);
			}
		} catch (ServletException e) {
			
			log.error(e.getMessage());
			return new ResponseEntity<Object>(new Message("Failed to login"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	public ResponseEntity<Object> register(@RequestBody User user){
		
		try {
			
			if(customerDataService.userExists(user.getUser())){
				
				return new ResponseEntity<Object>(new Message("User already exists."), HttpStatus.BAD_REQUEST);
			}
		
			customerDataService.saveUser(user.getUser(), user.getPassword());
			
			String token = "ab#r%h$mn" + user.getUser();
			
			sessionValidationService.registerUserSession(user.getUser(), token);
			return new ResponseEntity<Object>(new Token(token) , HttpStatus.OK);
			
		} catch (ServletException e) {
			
			log.error(e.getMessage());
			return new ResponseEntity<Object>(new Message("Failed to register user"), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
				
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.POST, produces="application/json")
	public ResponseEntity<Object> logout(@RequestHeader(value="Authorization", required=true) String authToken){
		
		try {
			sessionValidationService.unregisterUserSession(authToken);
			return new ResponseEntity<Object>(new Message("Logged out successfully"), HttpStatus.OK);
		} catch (ServletException e) {
			return new ResponseEntity<Object>(new Message("Failed to logout"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
				
	}

}
