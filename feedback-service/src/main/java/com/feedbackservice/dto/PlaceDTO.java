package com.feedbackservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PlaceDTO {

	private Integer placeId;
	private String placeName;
	private String images;
	private String address;
	private String area;
	private Double distance;
	private String description;
	private String tags;
}
