package com.feedbackservice.dto;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDto {
 
	private Integer placeId;
	private String placeName;
	private String feedbackText;
	private Timestamp dateTime;
 
}
