package com.hotel.service.payload;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse {

	private String message;
	private boolean success;
	private HttpStatus status;
}
