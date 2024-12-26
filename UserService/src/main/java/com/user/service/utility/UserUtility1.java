package com.user.service.utility;

import java.util.Random;

public class UserUtility1 {

	// Helper method to generate a random 5-digit number with a prefix
	public static String generateRandomUserId() {
	    Random random = new Random();
	    int numericId = 10000 + random.nextInt(90000); // Ensures a 5-digit number (10000–99999)
	    return "USER" + numericId; // Concatenate "USER" prefix
	}
}
