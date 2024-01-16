package com.placeservice;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.placeservice.advices.PlaceNotFoundException;
import com.placeservice.dto.PlaceDTO;
import com.placeservice.entity.Place;
import com.placeservice.repository.PlaceRepository;
import com.placeservice.serviceimpl.PlaceServiceImpl;

@SpringBootTest
class PlaceServiceApplicationTests {

	@Mock
	private PlaceRepository placeRepository;

	@InjectMocks
	private PlaceServiceImpl placeService;

	@Test
	public void testAddPlace() {
		// Arrange
		PlaceDTO placeDTO = createPlaceDTO();
		when(placeRepository.findByPlaceName(anyString())).thenReturn(null);
		when(placeRepository.save(any(Place.class))).thenReturn(new Place());

		// Act
		ResponseEntity<String> result = placeService.addPlace(placeDTO);

		// Assert
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		verify(placeRepository).save(any(Place.class));
	}

	@Test
	public void testGetAllPlaces() {
		// Arrange
		when(placeRepository.findAll()).thenReturn(Arrays.asList(new Place(), new Place()));

		// Act
		ResponseEntity<List<PlaceDTO>> result = placeService.getAllPlaces();

		// Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals(2, result.getBody().size());
	}

	@Test
	public void testGetPlaceById() throws Throwable {
		// Arrange
		int placeId = 1;
		when(placeRepository.findById(anyInt())).thenReturn(Optional.of(createPlaceEntity()));

		// Act
		ResponseEntity<PlaceDTO> result = placeService.getPlaceById(placeId);

		// Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());
	}

	@Test
	public void testGetPlaceByName() throws Throwable {
		// Arrange
		String placeName = "TestPlace";
		when(placeRepository.findAllByPlaceName(anyString())).thenReturn(Arrays.asList(createPlaceEntity()));

		// Act
		ResponseEntity<List<PlaceDTO>> result = placeService.getPlaceByName(placeName);

		// Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals(1, result.getBody().size());
	}

	@Test
	public void testDeletePlaceByName() throws Throwable {
		// Arrange
		String placeName = "TestPlace";
		when(placeRepository.findByPlaceName(anyString())).thenReturn(createPlaceEntity());

		// Act
		ResponseEntity<String> result = placeService.deletePlaceByPlaceName(placeName);

		// Assert
		assertEquals(HttpStatus.OK, result.getStatusCode());
		verify(placeRepository).delete(any(Place.class));
	}

	private PlaceDTO createPlaceDTO() {
		PlaceDTO placeDTO = new PlaceDTO();
		placeDTO.setPlaceName("TestPlace");
		// Set other fields as needed
		return placeDTO;
	}

	private Place createPlaceEntity() {
		Place place = new Place();
		place.setPlaceName("TestPlace");
		// Set other fields as needed
		return place;
	}

}
