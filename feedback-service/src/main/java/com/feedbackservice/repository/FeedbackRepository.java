package com.feedbackservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.feedbackservice.entity.Feedback;

@Repository
	public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	List<Feedback> findByUserId(Integer userId);
	}

	
	
	

