package com.tourplan.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tourplan.dto.ListOfTour;
import com.tourplan.dto.TourDetailsDTO;
import com.tourplan.dto.TourPlanResponseDTO;

public interface TourPlanService {

	ResponseEntity<String> updateTour(Integer tourId, TourDetailsDTO tourDetailsDto) throws Throwable;

	ResponseEntity<String> deleteTour(Integer tourId) throws Throwable;

	ResponseEntity<String> createPlan(Integer userId, TourDetailsDTO tourDetailsDto);

	ResponseEntity<TourPlanResponseDTO> getTourPlan(Integer tourId) throws Throwable;

	ResponseEntity<List<ListOfTour>> getTourByUserId(Integer userId) throws Throwable;

}
