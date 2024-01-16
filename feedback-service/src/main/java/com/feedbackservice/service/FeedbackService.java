package com.feedbackservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.feedbackservice.dto.FeedbackDto;
import com.feedbackservice.dto.UserDTO;
import com.feedbackservice.entity.Feedback;

public interface FeedbackService {

	ResponseEntity<FeedbackDto> getFeedback(Integer feedbackId) throws Throwable;

	ResponseEntity<List<Feedback>> getAllFeedback();

	ResponseEntity<String> updateFeedback(Integer feedbackId, FeedbackDto feedbackDTO) throws Throwable;

	ResponseEntity<String> deleteFeedback(Integer feedbackId) throws Throwable;

	ResponseEntity<String>getPlacefortour(Integer placeId, Integer userId, FeedbackDto feedbackdto) throws Throwable;

	ResponseEntity<UserDTO> getUserDTO(Integer feedbackId)throws Throwable;
	
	ResponseEntity<List<FeedbackDto>> getFeedBackByUserId(Integer userId) throws Throwable;
}
