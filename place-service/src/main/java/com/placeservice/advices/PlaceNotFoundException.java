package com.placeservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PlaceNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PlaceNotFoundException (String message){
		super(message);
	}
}
