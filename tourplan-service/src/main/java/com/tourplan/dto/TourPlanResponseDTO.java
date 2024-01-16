package com.tourplan.dto;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TourPlanResponseDTO {
	
	private Integer tourId;
    private UserDTO userdto;
    private List<TourDatesPlacesDTO> tourDatesPlacesList;

}
