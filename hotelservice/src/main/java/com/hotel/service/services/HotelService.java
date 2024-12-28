package com.hotel.service.services;

import java.util.List;

import com.hotel.service.entities.Hotel;

public interface HotelService {

	//create hotel
	Hotel createHotel(Hotel hotel);
	
	//get single hotel
	Hotel getSingleHotel(String hotelId);
	
	//get all list of hotel
	List<Hotel> getAllHotel();
	
	//update hotel
	Hotel updateHotel(String hotelId, Hotel hotel);
	
	//delete hotel
	void deleteHotel(String hotelId);
	
	//get hotel by hotel name
	List<Hotel> findByHotelName(String hotelName);
}
