package com.hotel.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.service.entities.Hotel;
import com.hotel.service.exception.ResourceNotFoundException;
import com.hotel.service.repository.HotelRepository;
import com.hotel.service.services.HotelService;
import com.hotel.service.utility.HotelServiceUtility1;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel createHotel(Hotel hotel) {
		hotel.setHotelId(HotelServiceUtility1.generateRandomHotelId());
		Hotel saveHotel = hotelRepository.save(hotel);
		return saveHotel;
	}

	@Override
	public Hotel getSingleHotel(String hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
				()-> new ResourceNotFoundException("hotel with given id not found : "+ hotelId));
		return hotel;
	}

	@Override
	public List<Hotel> getAllHotel() {
		List<Hotel> allHotels = hotelRepository.findAll();
		return allHotels;
	}

	@Override
	public Hotel updateHotel(String hotelId, Hotel hotel) {
		Hotel findHotel = hotelRepository.findById(hotelId).orElseThrow(
				()-> new ResourceNotFoundException("hotel with given id not found : "+ hotelId));
		findHotel.setHotelName(hotel.getHotelName());
		findHotel.setHotelLocation(hotel.getHotelLocation());
		findHotel.setAboutHotel(hotel.getAboutHotel());
		
		Hotel updatedHotel = hotelRepository.save(findHotel);
		return updatedHotel;
	}

	@Override
	public void deleteHotel(String hotelId) {
		hotelRepository.deleteById(hotelId);	
	}

	@Override
	public List<Hotel> findByHotelName(String hotelName) {
	 List<Hotel> hotelname = hotelRepository.findByHotelName(hotelName).orElseThrow(
				()-> new ResourceNotFoundException("hotel with given name not found : "+ hotelName));
		return hotelname;
	}



}
