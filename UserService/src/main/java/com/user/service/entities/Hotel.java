package com.user.service.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

	private String hotelId;
	private String hotelName;
	private String hotelLocation;
	private String aboutHotel;
	
//	@Transient
//	private List<Rating> rating = new ArrayList<Rating>();
	
	
	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", hotelLocation=" + hotelLocation
				+ ", aboutHotel=" + aboutHotel + "]";
	}

}
