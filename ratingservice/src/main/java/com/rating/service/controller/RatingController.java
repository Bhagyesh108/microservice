package com.rating.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.service.entities.Rating;
import com.rating.service.service.RatingService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	// create rating
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
	}

	// get all ratings
	@GetMapping
	public ResponseEntity<List<Rating>> getAllRating() {
		return ResponseEntity.ok(ratingService.getRatings());
	}

	// get rating based on user Id
	//in path variable we no need to pass vale if String and get mapping vale are same
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable("userId") String userId) {
		return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
	}

	// get rating based on hotel Id
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable("hotelId") String hotelId) {
		return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
	}
}
