package com.rating.service.service;

import java.util.List;

import com.rating.service.entities.Rating;

public interface RatingService {

	//create
	Rating create(Rating rating);
	
	//get all rating
	List<Rating> getRatings();
	
	//get all rating by user Id
	List<Rating> getRatingByUserId(String userId);
	
	//get all rating by hotel id
	List<Rating> getRatingByHotelId(String hotelId);
}
