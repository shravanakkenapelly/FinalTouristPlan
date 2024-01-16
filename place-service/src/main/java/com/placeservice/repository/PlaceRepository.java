package com.placeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.placeservice.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {


	Place findByTags(String tags);

	@Query(value = "SELECT p.placeId FROM Place p")
	List<Integer> findAllPlaceIds();

	Place findByPlaceName(String placeName);

	List<Place> findAllByPlaceName(String placeName);
}
