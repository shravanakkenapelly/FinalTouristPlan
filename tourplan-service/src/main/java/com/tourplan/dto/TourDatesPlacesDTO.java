package com.tourplan.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TourDatesPlacesDTO {

	private Integer planId;
	private String tourDate;
	private PlaceDTO placeDto;

}
