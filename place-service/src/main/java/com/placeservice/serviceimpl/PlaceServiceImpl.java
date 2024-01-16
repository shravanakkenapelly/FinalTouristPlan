package com.placeservice.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.placeservice.advices.PlaceNotFoundException;
import com.placeservice.dto.PlaceDTO;
import com.placeservice.entity.Place;
import com.placeservice.repository.PlaceRepository;
import com.placeservice.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	private PlaceRepository placerepository;

	@Override
	public ResponseEntity<String> addPlace(PlaceDTO placeDto) {
		Place place = new Place();

		Place placeName = placerepository.findByPlaceName(placeDto.getPlaceName());
		if (placeName != null) {
			if (placeName.getPlaceName().equalsIgnoreCase(placeDto.getPlaceName())) {
				return new ResponseEntity<>("Place already exists, Choose a different place name", HttpStatus.CONFLICT);
			}
		} else {
			place.setPlaceName(placeDto.getPlaceName());
			place.setImages(placeDto.getImages());
			place.setAddress(placeDto.getAddress());
			place.setArea(placeDto.getArea());
			place.setDistance(placeDto.getDistance());
			place.setDescription(placeDto.getDescription());
			place.setTags(placeDto.getTags());
			placerepository.save(place);
		}
		return new ResponseEntity<>("Places added succesfully", HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
		List<PlaceDTO> listDtoplace = new ArrayList<>();
		List<Place> listEntity = placerepository.findAll();
		for (Place place : listEntity) {
			PlaceDTO placeDTO = new PlaceDTO();
			placeDTO.setPlaceId(place.getPlaceId());
			placeDTO.setPlaceName(place.getPlaceName());
			placeDTO.setImages(place.getImages());
			placeDTO.setAddress(place.getAddress());
			placeDTO.setArea(place.getArea());
			placeDTO.setDistance(place.getDistance());
			placeDTO.setDescription(place.getDescription());
			placeDTO.setTags(place.getTags());
			listDtoplace.add(placeDTO);
		}
		return new ResponseEntity<>(listDtoplace, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updatePlaces(PlaceDTO placeDto) throws Throwable {

		Place place = placerepository.findById(placeDto.getPlaceId()).get();

		
		if (place != null) {
//			place.setPlaceId(placeDto.getPlaceId());
			place.setPlaceName(placeDto.getPlaceName());
			place.setImages(placeDto.getImages());
			place.setAddress(placeDto.getAddress());
			place.setArea(placeDto.getArea());
			place.setDistance(placeDto.getDistance());
			place.setDescription(placeDto.getDescription());
			place.setTags(placeDto.getTags());
			placerepository.save(place);
			return new ResponseEntity<>("Details Updated Successfully", HttpStatus.OK);
		} else {
			throw new PlaceNotFoundException(placeDto.getPlaceName() + " is not found");
		}
	}

	@Override
	public ResponseEntity<PlaceDTO> getPlaceById(Integer placeId) throws Throwable {
		PlaceDTO placeDTO = new PlaceDTO();

		Place place = placerepository.findById(placeId).get();
		if (place != null) {

			placeDTO.setPlaceId(place.getPlaceId());
			placeDTO.setPlaceName(place.getPlaceName());
			placeDTO.setImages(place.getImages());
			placeDTO.setAddress(place.getAddress());
			placeDTO.setArea(place.getArea());
			placeDTO.setDistance(place.getDistance());
			placeDTO.setDescription(place.getDescription());
			placeDTO.setTags(place.getTags());
			return new ResponseEntity<>(placeDTO, HttpStatus.OK);
		} else
			throw new PlaceNotFoundException(placeId + " is not found");

	}

	@Override
	public ResponseEntity<List<PlaceDTO>> getPlaceByName(String placeName) throws Throwable {
		List<Place> place = placerepository.findAllByPlaceName(placeName);
		List<PlaceDTO> list = new ArrayList<>();
		if (place != null) {
			for (Place place1 : place) {
				PlaceDTO placeDTO = new PlaceDTO();
				placeDTO.setPlaceId(place1.getPlaceId());
				placeDTO.setPlaceName(place1.getPlaceName());
				placeDTO.setImages(place1.getImages());
				placeDTO.setAddress(place1.getAddress());
				placeDTO.setArea(place1.getArea());
				placeDTO.setDistance(place1.getDistance());
				placeDTO.setDescription(place1.getDescription());
				placeDTO.setTags(place1.getTags());
				list.add(placeDTO);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else
			throw new PlaceNotFoundException(placeName + " is not found");
	}

	@Override
	public ResponseEntity<String> deletePlaceByPlaceName(String placeName) throws Throwable {
		Place place = placerepository.findByPlaceName(placeName);
		if (place != null) {
			placerepository.delete(place);
			return new ResponseEntity<>("Place Deleted", HttpStatus.OK);
		} else
			throw new PlaceNotFoundException(placeName + " is not found");

	}

	@Override
	public ResponseEntity<PlaceDTO> getPlaceByTags(String tags) throws Throwable {
		Place place = placerepository.findByTags(tags);
		if (place != null) {
			PlaceDTO placeDTO = new PlaceDTO();
			placeDTO.setPlaceId(place.getPlaceId());
			placeDTO.setPlaceName(place.getPlaceName());
			placeDTO.setImages(place.getImages());
			placeDTO.setAddress(place.getAddress());
			placeDTO.setArea(place.getArea());
			placeDTO.setDistance(place.getDistance());
			placeDTO.setDescription(place.getDescription());
			placeDTO.setTags(place.getTags());
			return new ResponseEntity<>(placeDTO, HttpStatus.OK);
		} else
			throw new PlaceNotFoundException(tags + " is not found");

	}

	@Override
	public Integer getPlacefortour(Integer placeId) throws Throwable {
		Place placeId1 = placerepository.findById(placeId).get();
		if (placeId1 != null) {
			Integer placeId2 = placeId1.getPlaceId();
			return placeId2;
		} else
			throw new PlaceNotFoundException("place is not found with this Id");

	}

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<List<PlaceDTO>> getListOfPlaceDTOByPlaceId(List<Integer> placeIds) throws Throwable {

		List<Place> place = new ArrayList<>();
		List<PlaceDTO> placeDTOs = new ArrayList<>();
//		getting place for each integer i from db
		for (Integer i : placeIds) {
			place.add(placerepository.findById(i).get());
		}

		if (place != null) {
			for (Place place1 : place) {
				PlaceDTO placeDTO = new PlaceDTO();
				placeDTO.setPlaceId(place1.getPlaceId());
				placeDTO.setPlaceName(place1.getPlaceName());
				placeDTO.setImages(place1.getImages());
				placeDTO.setAddress(place1.getAddress());
				placeDTO.setArea(place1.getArea());
				placeDTO.setDistance(place1.getDistance());
				placeDTO.setDescription(place1.getDescription());
				placeDTO.setTags(place1.getTags());
				placeDTOs.add(placeDTO);
			}
			return new ResponseEntity<>(placeDTOs, HttpStatus.OK);
		} else
			throw new PlaceNotFoundException("placeId's are not found.");

	}

	@Override
	public ResponseEntity<List<Integer>> getListOfPlaceIdsl(List<Integer> placeIds) throws Throwable {

		List<Integer> placesId = new ArrayList<>();

		for (Integer i : placeIds) {
			Place place = placerepository.findById(i).get();
			placesId.add(place.getPlaceId());
		}
		if (placesId != null)
			return new ResponseEntity<>(placesId, HttpStatus.OK);
		else
			throw new PlaceNotFoundException("placeId's are not found.");

	}

	@Override
	public ResponseEntity<String> deletePlaceById(Integer placeId) throws Throwable {
		Place place = placerepository.findById(placeId).get();
		if(place!=null) {
			placerepository.delete(place);
			return new ResponseEntity<>("Place Deleted", HttpStatus.OK);
		}else throw new PlaceNotFoundException("Place not found for this Id");
		
	}
	
	public void linkImageToPlace(int placeId, String imagePath) {
		// TODO Auto-generated method stub
		Place place = placerepository.findById(placeId).get();
		place.setImages(imagePath);

		placerepository.save(place);

	}

}
