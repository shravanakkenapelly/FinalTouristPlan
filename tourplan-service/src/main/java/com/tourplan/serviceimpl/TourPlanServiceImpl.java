package com.tourplan.serviceimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tourplan.advices.PlaceNotFoundException;
import com.tourplan.advices.TourNotFoundException;
import com.tourplan.dto.GenerateDate;
import com.tourplan.dto.ListOfTour;
import com.tourplan.dto.PlaceDTO;
import com.tourplan.dto.TourDatesPlacesDTO;
import com.tourplan.dto.TourDetailsDTO;
import com.tourplan.dto.TourPlanResponseDTO;
import com.tourplan.dto.UserDTO;
import com.tourplan.entity.Tour;
import com.tourplan.entity.TourDatesPlaces;
import com.tourplan.openfeign.PlaceClient;
import com.tourplan.openfeign.UserClient;
import com.tourplan.repository.TourDatesPlacesRepository;
import com.tourplan.repository.TourRepository;
import com.tourplan.service.TourPlanService;

@Service
public class TourPlanServiceImpl implements TourPlanService {

	private static final Logger logger = LoggerFactory.getLogger(TourPlanServiceImpl.class);

	@Autowired
	PlaceClient placeClients;

	@Autowired
	UserClient userClients;

	@Autowired
	TourRepository tourPlanRepo;

	@Autowired
	TourDatesPlacesRepository DatesPlaceRepo;

	@Override
	public ResponseEntity<TourPlanResponseDTO> getTourPlan(Integer tourId) throws Throwable {
		logger.info("Fetching tour plan details for tourId: {}", tourId);
		Optional<Tour> tourOptional = tourPlanRepo.findById(tourId);

		if (tourOptional.isPresent()) {
			Tour tour = tourOptional.get();

			logger.info("Fetching TourDatesPlaces for tourId: {}", tourId);
			List<TourDatesPlaces> tourDatesPlacesList = DatesPlaceRepo.findByTourId(tourId);

			TourPlanResponseDTO responseDTO = new TourPlanResponseDTO();
			responseDTO.setTourId(tour.getTourId());

			logger.info("Fetching User details for userId: {}", tour.getUserId());
			UserDTO userDTO = userClients.getUserById(tour.getUserId()).getBody();

			UserDTO userDTO2 = new UserDTO();
			userDTO2.setUserId(userDTO.getUserId());
			userDTO2.setUserName(userDTO.getUserName());
			userDTO2.setUserEmail(userDTO.getUserEmail());
			userDTO2.setNumber(userDTO.getNumber());
			userDTO2.setAddress(userDTO.getAddress());
			responseDTO.setUserdto(userDTO2);

			List<Integer> placeIds = tourDatesPlacesList.stream().map(TourDatesPlaces::getPlaceId)
					.collect(Collectors.toList());

			ResponseEntity<List<PlaceDTO>> placeDTOResponse = placeClients.getListOfPlaceDTOByPlaceId(placeIds);

			if (placeDTOResponse.getStatusCode() == HttpStatus.OK) {
				List<PlaceDTO> list2 = placeDTOResponse.getBody();

				List<TourDatesPlacesDTO> tourDatesPlacesDTOList = new LinkedList<>();
				for (TourDatesPlaces tourDatesPlaces : tourDatesPlacesList) {
					TourDatesPlacesDTO dto = new TourDatesPlacesDTO();
					dto.setPlanId(tourDatesPlaces.getPlanId());
					dto.setTourDate(tourDatesPlaces.getTourDate());

					Optional<PlaceDTO> placeDTOOptional = list2.stream()
							.filter(placeDTO -> placeDTO.getPlaceId().equals(tourDatesPlaces.getPlaceId())).findFirst();

					if (placeDTOOptional.isPresent()) {
						dto.setPlaceDto(placeDTOOptional.get());
					} else {
						throw new PlaceNotFoundException("Place details not found");
					}

					tourDatesPlacesDTOList.add(dto);
				}

				responseDTO.setTourDatesPlacesList(tourDatesPlacesDTOList);

				logger.info("Retrieved tour plan details successfully for tourId: {}", tourId);
				return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			} else {
				throw new PlaceNotFoundException("Place details not found");
			}
		} else {
			throw new TourNotFoundException("Tour Details not found");
		}
	}

	@Override
	public ResponseEntity<String> updateTour(Integer tourId, TourDetailsDTO tourDetailsDto) throws Throwable {
		logger.info("Updating tour plan details for tourId: {}", tourId);
		Optional<Tour> tourOptional = tourPlanRepo.findById(tourId);

		if (tourOptional.isEmpty()) {
			throw new TourNotFoundException("Tour Details not found");
		}

		Tour existingTour = tourOptional.get();
		List<TourDatesPlaces> existingTourDatesPlaces = DatesPlaceRepo.findByTourId(tourId);

		LocalDate startDate = LocalDate.parse(tourDetailsDto.getStartDate());
		LocalDate endDate = LocalDate.parse(tourDetailsDto.getEndDate());
		List<String> tourDates = GenerateDate.generateDatesBetween(startDate, endDate);

		List<Integer> placeIds = tourDetailsDto.getPlaceIds();

		if (existingTourDatesPlaces.size() != placeIds.size()) {
			throw new IllegalArgumentException("Mismatched details for existing Dates or Places ");
		}

		for (int i = 0; i < existingTourDatesPlaces.size(); i++) {
			String tourDate = tourDates.get(i);
			TourDatesPlaces tourDatesPlaces = existingTourDatesPlaces.get(i);
			Integer selectedPlaceId = placeIds.get(i);

			tourDatesPlaces.setPlaceId(selectedPlaceId);
			tourDatesPlaces.setTourDate(tourDate);

			DatesPlaceRepo.save(tourDatesPlaces);
		}
		logger.info("Tour plan details updated successfully for tourId: {}", tourId);
		return new ResponseEntity<>("Details Updated", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteTour(Integer tourId) throws Throwable {
		logger.info("Deleting tour plan for tourId: {}", tourId);
		List<TourDatesPlaces> tourDatesPlacesList = DatesPlaceRepo.findByTourId(tourId);

		if (tourDatesPlacesList.isEmpty()) {
			throw new TourNotFoundException("Tour Details not found");
		}

		DatesPlaceRepo.deleteAll(tourDatesPlacesList);

		if (tourPlanRepo.existsById(tourId)) {
			tourPlanRepo.deleteById(tourId);
			logger.info("Tour plan deleted successfully for tourId: {}", tourId);
			return new ResponseEntity<>("Tour Deleted", HttpStatus.OK);
		} else {
			throw new TourNotFoundException("Tour Details not found");
		}
	}

	@Override
	public ResponseEntity<String> createPlan(Integer userId, TourDetailsDTO tourDetailsDto) {
		logger.info("Creating tour plan for userId: {}", userId);
		Integer userIdForClient = userClients.getUserForClient(userId);

		Tour tourDetails = new Tour();
		tourDetails.setUserId(userIdForClient);

		tourPlanRepo.save(tourDetails);

		LocalDate startDate = LocalDate.parse(tourDetailsDto.getStartDate());
		LocalDate endDate = LocalDate.parse(tourDetailsDto.getEndDate());
		List<String> tourDates = GenerateDate.generateDatesBetween(startDate, endDate);

		List<Integer> placeIds = placeClients.getListOfPlaceIds(tourDetailsDto.getPlaceIds()).getBody();

		for (int i = 0; i < tourDates.size(); i++) {
			String tourDate = tourDates.get(i);
			Integer selectedPlaceId = placeIds.get(i);

			TourDatesPlaces tourDatesPlaces = new TourDatesPlaces();
			tourDatesPlaces.setTourId(tourDetails.getTourId());
			tourDatesPlaces.setPlaceId(selectedPlaceId);
			tourDatesPlaces.setTourDate(tourDate);
			DatesPlaceRepo.save(tourDatesPlaces);
		}
		logger.info("Tour plan created successfully for userId: {}", userId);
		return new ResponseEntity<>("Created", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<ListOfTour>> getTourByUserId(Integer userId) throws Throwable {
		logger.info("Fetching tours for userId: {}", userId);

		// Retrieve tours for the given userId
		List<Tour> tours = tourPlanRepo.findByUserId(userId);

		if (tours.isEmpty()) {
			throw new TourNotFoundException("No tours found for the given userId");
		}

		List<ListOfTour> listOfTourList = new ArrayList<>();

		for (Tour tour : tours) {
			Integer tourId = tour.getTourId();
			List<TourDatesPlaces> tourDatesPlacesList = DatesPlaceRepo.findByTourId(tourId);

			// Create a list to store TourDatesPlacesDTO for the current tour
			List<TourDatesPlacesDTO> tourDatesPlacesDTOList = new ArrayList<>();

			// Fetch PlaceDTOs for the current tour
			List<Integer> placeIds = tourDatesPlacesList.stream().map(TourDatesPlaces::getPlaceId)
					.collect(Collectors.toList());

			List<PlaceDTO> placeDTOList = placeClients.getListOfPlaceDTOByPlaceId(placeIds).getBody();

			// Create TourDatesPlacesDTO objects for the current tour
			for (TourDatesPlaces tourDatesPlaces : tourDatesPlacesList) {
				TourDatesPlacesDTO tourDatesPlacesDTO = new TourDatesPlacesDTO();
				tourDatesPlacesDTO.setPlanId(tourDatesPlaces.getPlanId());
				tourDatesPlacesDTO.setTourDate(tourDatesPlaces.getTourDate());

				// Find the corresponding PlaceDTO for the current TourDatesPlaces
				Optional<PlaceDTO> placeDTOOptional = placeDTOList.stream()
						.filter(placeDTO -> placeDTO.getPlaceId().equals(tourDatesPlaces.getPlaceId())).findFirst();

				// Set the PlaceDTO if found
				placeDTOOptional.ifPresent(tourDatesPlacesDTO::setPlaceDto);

				tourDatesPlacesDTOList.add(tourDatesPlacesDTO);
			}

			// Create ListOfTour for the current tour
			ListOfTour listOfTour = new ListOfTour();
			listOfTour.setTourId(tourId);
			listOfTour.setTourDatesPlacesList(tourDatesPlacesDTOList);

			listOfTourList.add(listOfTour);
		}

		return new ResponseEntity<>(listOfTourList, HttpStatus.OK);
	}

}
