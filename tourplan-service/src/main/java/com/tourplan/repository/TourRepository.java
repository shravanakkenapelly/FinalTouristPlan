package com.tourplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tourplan.entity.Tour;

public interface TourRepository extends JpaRepository<Tour, Integer> {

	List<Tour> findByUserId(Integer userId);
	

}
