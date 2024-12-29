package com.rating.service.utility;

import java.util.Random;

public class RatingUtility1 {

	// Helper method to generate a random 5-digit number with a prefix
	public static String generateRandomRatingId() {
	    Random random = new Random();
	    int numericId = 10000 + random.nextInt(90000); // Ensures a 5-digit number (10000–99999)
	    return "RATING" + numericId; // Concatenate "USER" prefix
	}
}
