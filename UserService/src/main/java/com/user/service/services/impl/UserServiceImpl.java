package com.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exception.ResourceNotFoundException;
import com.user.service.repository.UserRepository;
import com.user.service.services.UserService;
import com.user.service.utility.UserUtility1;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	//create user
	@Override
	public User saveUser(User user) {
		user.setUserId(UserUtility1.generateRandomUserId());
		User saveuser = userRepository.save(user);
		return saveuser;
	}

	//get all users
	@Override
	public List<User> getAllUser() {
		
		List<User> allUsers = userRepository.findAll();
		
		// Iterate over the list of users to fetch their ratings and hotels
	    allUsers.forEach(user -> {
	        try {
	            // Fetch ratings for the current user from Rating Service
	            Rating[] ratingsArray = restTemplate.getForObject(
	                "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
	                Rating[].class
	            );

	            // Convert the  Rating[] array to a List for easier manipulation
	            List<Rating> ratings = Arrays.asList(ratingsArray);

	            // Iterate over ratings to fetch hotel details for each
	            ratings.forEach(rating -> {
	                try {
	                    // Fetch hotel details for the current rating
	                    Hotel hotel = restTemplate.getForObject(
	                        "http://HOTELSERVICE/hotels/" + rating.getHotelId(),
	                        Hotel.class
	                    );
	                    // Set the fetched hotel in the rating
	                    rating.setHotel(hotel);
	                } catch (Exception e) {
	                    logger.error("Error fetching hotel details for rating ID {}: {}", rating.getHotelId(), e.getMessage());
	                }
	            });

	            // Set the complete list of ratings (with hotels) in the user object
	            user.setRating(ratings);

	        } catch (Exception e) {
	            logger.error("Error fetching ratings for user ID {}: {}", user.getUserId(), e.getMessage());
	        }
	    });

	    // Return the list of users with ratings and hotels
	    return allUsers;
	}

	//get single user by id
	@Override
	public User getUser(String userId) {
		
		//get user from database with the help of user repository
		User userid = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		
		//we are calling rating service from userservice, with URL:-http://localhost:8083/ratings/users/USER79147
		//fetching rating for the above user from RATING-SERVICE
		
			Rating[] ratingfOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+userid.getUserId(), Rating[].class);
			logger.info("printing forObject for getUser : {}",ratingfOfUser);
			
			List<Rating> list = Arrays.stream(ratingfOfUser).toList();
			
			List<Rating> ratingList = list.stream().map(rating -> {
				
				//api call to hotel service to get the hotel
				//url :- http://localhost:8082/hotels/HOTEL94482
				Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
				logger.info("printing forObject for hotels : {}",hotel.toString());
				
				//set the hotel to rating
				rating.setHotel(hotel);
				
				//return the rating
				return rating;
				
			}).collect(Collectors.toList());
			userid.setRating(ratingList);
				
		return userid;
	}

	//update user
	@Override
	public User updateUser(User user, String userId) {
		User findUser = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		findUser.setUserName(user.getUserName());
		findUser.setUserEmail(user.getUserEmail());
		findUser.setRating(user.getRating());
		findUser.setAboutUser(user.getAboutUser());
		
		User updatedUser = userRepository.save(findUser);
		return updatedUser;
	}

	//delete user
	@Override
	public void deleteUser(String userId) {
		User userid = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException
				("user with given id not found : "+userId));
		userRepository.delete(userid);
		
	}

	//get user by mail
	@Override
	public User getUserByEmail(String email) {
		User findByEmail = userRepository.findByUserEmail(email).orElseThrow(()-> new ResourceNotFoundException
				("user not found with emailid "+ email));
		return findByEmail;
	}
	
}
