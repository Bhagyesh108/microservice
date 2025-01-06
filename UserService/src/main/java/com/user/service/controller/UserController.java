package com.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.User;
import com.user.service.payload.ApiResponse;
import com.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//create user
	@PostMapping("/create")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
	}
	
	/**
	 * this api is calling two service in UserServiceimpl class so we are using circuit breaker on this api
	 * for this method ratingHotelFallBack the return type must be same
	 * */
	
	//get single user
	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId){
		User user = userService.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	//creating fall back method for ciruit breaker if any service down then only this method will execute
	public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex){
		logger.info("fall back is executed bcz service is down : {}",ex.getMessage());
		User user = User.builder()
		       .userEmail("dummy@gmail.com")
		       .userName("dummy")
		       .aboutUser("this user is created bcz some services are down")
		       .userId("123")
		       .build();
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	//get all user
	@GetMapping
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelBreakerforAllUser")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(allUser);
	}
	
	//creating fall back method for ciruit breaker if any service down then only this method will execute
		public ResponseEntity<List<User>> ratingHotelBreakerforAllUser(Exception ex){
			logger.info("fall back is executed bcz service is down : {}",ex.getMessage());
			User user = User.builder()
			       .userEmail("dummy@gmail.com")
			       .userName("dummy")
			       .aboutUser("this user is created in getAllUser bcz some services are down")
			       .userId("123")
			       .build();
			return new ResponseEntity<List<User>>(HttpStatus.OK);
		}
	


	
	//update user
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable("userId") String userId){
		User updateUser = userService.updateUser(user, userId);
		return  ResponseEntity.ok(updateUser);
	}
		
	//delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String userId){
		userService.deleteUser(userId);
		ApiResponse message = ApiResponse
				.builder()
				.message("user deleted sucessfully !! "+userId)
				.status(HttpStatus.OK)
				.success(true)
				.build();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
			
	//get single user by email
	@GetMapping("/email/{emailId}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("emailId") String emailId){
		User userByEmail = userService.getUserByEmail(emailId);
		return ResponseEntity.status(HttpStatus.OK).body(userByEmail);
	}
}
