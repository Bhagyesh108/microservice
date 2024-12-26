package com.user.service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.user.service.entities.User;

@Service
public interface UserService {

	//create user
	User saveUser(User user);
	
	//getAlluser
	List<User> getAllUser();
	
	//getSingle user
	User getUser(String userId);
	
	//update user
	User updateUser(User user, String userId);
	
	//delete user
	void deleteUser(String userId);
	
	//get single user by email
	User getUserByEmail(String email);
}
