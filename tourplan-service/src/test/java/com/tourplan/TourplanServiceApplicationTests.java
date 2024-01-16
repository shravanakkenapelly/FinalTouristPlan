package com.tourplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tourplan.advices.TourNotFoundException;
import com.tourplan.dto.PlaceDTO;
import com.tourplan.dto.TourPlanResponseDTO;
import com.tourplan.dto.UserDTO;
import com.tourplan.entity.Tour;
import com.tourplan.entity.TourDatesPlaces;
import com.tourplan.openfeign.PlaceClient;
import com.tourplan.openfeign.UserClient;
import com.tourplan.repository.TourDatesPlacesRepository;
import com.tourplan.repository.TourRepository;
import com.tourplan.serviceimpl.TourPlanServiceImpl;

@SpringBootTest
class TourplanServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private TourRepository tourRepository;

	@Mock
	private TourDatesPlacesRepository datesPlacesRepository;

	@Mock
	private UserClient userClient;

	@Mock
	private PlaceClient placeClient;

	@InjectMocks
	private TourPlanServiceImpl tourPlanService;

	@Test
	void getTourPlan_Success() throws Throwable {
		// Mock data
		int tourId = 1;
		int userId = 1;

		Tour tour = new Tour();
		tour.setTourId(tourId);
		tour.setUserId(userId);

		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setUserName("John Doe");

		TourDatesPlaces tourDatesPlaces = new TourDatesPlaces();
		tourDatesPlaces.setPlanId(1);
		tourDatesPlaces.setTourId(tourId);
		tourDatesPlaces.setPlaceId(1);
		tourDatesPlaces.setTourDate("2023-01-01");

		List<TourDatesPlaces> tourDatesPlacesList = new ArrayList<>();
		tourDatesPlacesList.add(tourDatesPlaces);

		PlaceDTO placeDTO = new PlaceDTO();
		placeDTO.setPlaceId(1);
		placeDTO.setPlaceName("Test Place");

		List<PlaceDTO> placeDTOList = Arrays.asList(placeDTO);

		// Mock behaviors
		when(tourRepository.findById(tourId)).thenReturn(Optional.of(tour));
		when(datesPlacesRepository.findByTourId(tourId)).thenReturn(tourDatesPlacesList);
		when(userClient.getUserById(userId)).thenReturn(ResponseEntity.ok(userDTO));
		when(placeClient.getListOfPlaceDTOByPlaceId(any())).thenReturn(ResponseEntity.ok(placeDTOList));

		// Execute
		ResponseEntity<TourPlanResponseDTO> response = tourPlanService.getTourPlan(tourId);

		// Verify
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(tourId, response.getBody().getTourId());
		assertEquals(userId, response.getBody().getUserdto().getUserId());
		assertEquals("John Doe", response.getBody().getUserdto().getUserName());
		assertEquals(1, response.getBody().getTourDatesPlacesList().size());
		assertEquals("2023-01-01", response.getBody().getTourDatesPlacesList().get(0).getTourDate());
		assertEquals("Test Place", response.getBody().getTourDatesPlacesList().get(0).getPlaceDto().getPlaceName());

		// Verify interactions
		verify(tourRepository, times(1)).findById(tourId);
		verify(datesPlacesRepository, times(1)).findByTourId(tourId);
		verify(userClient, times(1)).getUserById(userId);
		verify(placeClient, times(1)).getListOfPlaceDTOByPlaceId(any());
	}

	@Test
	void getTourPlan_TourNotFound() {
		// Mock data
		int tourId = 1;

		// Mock behavior
		when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

		// Execute and Verify
		assertThrows(TourNotFoundException.class, () -> tourPlanService.getTourPlan(tourId));

		// Verify interactions
		verify(tourRepository, times(1)).findById(tourId);
		verifyNoInteractions(datesPlacesRepository, userClient, placeClient);
	}

	// Add more test cases for other methods (updateTour, deleteTour, createPlan)

}
