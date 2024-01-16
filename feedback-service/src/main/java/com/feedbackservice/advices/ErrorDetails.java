package com.feedbackservice.advices;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

	private LocalDateTime ldt;
	private String message;
	private String details;

}