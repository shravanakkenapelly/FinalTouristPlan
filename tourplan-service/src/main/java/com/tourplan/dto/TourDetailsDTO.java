package com.tourplan.dto;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TourDetailsDTO {
	
	private Integer userId;
	private String startDate;
	private String endDate;
	private List<Integer> placeIds;

}
