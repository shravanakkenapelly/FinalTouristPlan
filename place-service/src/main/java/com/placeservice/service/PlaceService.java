package com.placeservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.placeservice.dto.PlaceDTO;

public interface PlaceService {

	ResponseEntity<String> addPlace(PlaceDTO placeDto);

	ResponseEntity<List<PlaceDTO>> getAllPlaces();

	ResponseEntity<String> updatePlaces(PlaceDTO placeDto) throws Throwable;

	ResponseEntity<PlaceDTO> getPlaceById(Integer id) throws Throwable;

	ResponseEntity<List<PlaceDTO>> getPlaceByName(String placeName) throws Throwable;

	ResponseEntity<String> deletePlaceByPlaceName(String placeName) throws Throwable;

	ResponseEntity<PlaceDTO> getPlaceByTags(String tags) throws Throwable;

	Integer getPlacefortour(Integer placeId) throws Throwable;

	ResponseEntity<List<PlaceDTO>> getListOfPlaceDTOByPlaceId(List<Integer> placeIds) throws Throwable;

	ResponseEntity<List<Integer>> getListOfPlaceIdsl(List<Integer> placeIds) throws Throwable;

	ResponseEntity<String> deletePlaceById(Integer placeId) throws Throwable;
	public void linkImageToPlace(int placeId, String imagePath);

}
