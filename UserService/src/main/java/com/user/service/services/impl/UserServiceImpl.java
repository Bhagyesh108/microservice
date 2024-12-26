package com.user.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.service.entities.User;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.services.UserService;
import com.user.service.utility.UserUtility1;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		user.setUserId(UserUtility1.generateRandomUserId());
		User saveuser = userRepository.save(user);
		return saveuser;
	}

	@Override
	public List<User> getAllUser() {
		List<User> allUser = userRepository.findAll();
		return allUser;
	}

	@Override
	public User getUser(String userId) {
		User userid = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		return userid;
	}


	@Override
	public User updateUser(User user, String userId) {
		User findUser = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		findUser.setUserName(user.getUserName());
		findUser.setUserEmail(user.getUserEmail());
	//	findUser.setRating(user.getRating());
		findUser.setAboutUser(user.getAboutUser());
		
		User updatedUser = userRepository.save(findUser);
		return updatedUser;
	}

	@Override
	public void deleteUser(String userId) {
		User userid = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		userRepository.delete(userid);
		
	}

	@Override
	public User getUserByEmail(String email) {
		User findByEmail = userRepository.findByUserEmail(email).orElseThrow(()-> new ResourceNotFoundException
				("user not found with emailid "+ email));
		return findByEmail;
	}
	
}
