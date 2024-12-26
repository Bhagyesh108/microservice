package com.user.service.controller;

import java.util.List;

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

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create user
	@PostMapping("/create")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User saveUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
	}
	
	//get single user
	@GetMapping("/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId){
		User user = userService.getUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	//get all user
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(allUser);
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
