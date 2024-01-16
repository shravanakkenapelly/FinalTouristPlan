package com.tourplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tourplan.entity.TourDatesPlaces;

public interface TourDatesPlacesRepository extends JpaRepository<TourDatesPlaces, Integer> {

	List<TourDatesPlaces> findByTourId(Integer tourId);

	/*
	 * Object findAllById(Integer tourId);
	 * 
	 * Object findAllByTourId(Integer tourId);
	 */
}
