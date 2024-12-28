package com.hotel.service.controller;

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

import com.hotel.service.entities.Hotel;
import com.hotel.service.payload.ApiResponse;
import com.hotel.service.services.HotelService;

@RestController
@RequestMapping("hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;

	//create
	@PostMapping("/create")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
	}
	
	//get single
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getSingleHotel(@PathVariable("hotelId") String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getSingleHotel(hotelId));
	}
	
	//get all
	@GetMapping
	public ResponseEntity<List<Hotel>> getAllHotels(){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotel());
	}
	
	//update
	@PutMapping("/{hotelId}")
	public ResponseEntity<Hotel> updateHotel(@PathVariable("hotelId") String hotelId,@RequestBody Hotel hotel){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.updateHotel(hotelId,hotel));	
	}
	
	//delete
	@DeleteMapping("/{hotelId}")
	public ResponseEntity<ApiResponse> deleteHotel(@PathVariable("hotelId") String hotelId){
		 hotelService.deleteHotel(hotelId);
		ApiResponse message = ApiResponse
					.builder()
					.message("hotel deleted sucessfully !! "+hotelId)
					.status(HttpStatus.OK)
					.success(true)
					.build();
			return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	//get by name
	@GetMapping("/hotelname/{hotelname}")
	public ResponseEntity<List<Hotel>> getHotelByName(@PathVariable("hotelname") String hotelname){
		List<Hotel> hotelByName = hotelService.findByHotelName(hotelname);
		return ResponseEntity.status(HttpStatus.OK).body(hotelByName);
	}
}
