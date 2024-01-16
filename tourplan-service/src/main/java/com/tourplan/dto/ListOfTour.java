package com.tourplan.dto;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class ListOfTour {
	
	private Integer tourId;
    private List<TourDatesPlacesDTO> tourDatesPlacesList;


}
