package com.feedbackservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FeedBackNotFound extends Exception {

	private static final long serialVersionUID = 1L;

	public FeedBackNotFound(String message) {
		super(message);
	}

}
