package com.feedbackservice.serviceimpl;
 
import java.sql.Timestamp;

import java.util.LinkedList;

import java.util.List;

import java.util.TimeZone;
 
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
 
import com.feedbackservice.advices.FeedBackNotFound;

import com.feedbackservice.dto.FeedbackDto;

import com.feedbackservice.dto.PlaceDTO;

import com.feedbackservice.dto.UserDTO;

import com.feedbackservice.entity.Feedback;

import com.feedbackservice.openfeign.PlaceClient;

import com.feedbackservice.openfeign.UserClient;

import com.feedbackservice.repository.FeedbackRepository;

import com.feedbackservice.service.FeedbackService;
 
@Service

public class FeedbackServiceImpl implements FeedbackService {
 
	private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
 
	@Autowired

	private FeedbackRepository feedbackrepo;
 
	@Autowired

	private PlaceClient placeClient;
 
	@Autowired

	private UserClient userclient;
 
	@Override

	public ResponseEntity<List<Feedback>> getAllFeedback() {

		logger.info("Fetching all feedback");

		List<Feedback> list = feedbackrepo.findAll();

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
 
	@Override

	public ResponseEntity<String> updateFeedback(Integer feedbackId, FeedbackDto feedbackDTO) throws Throwable {

		logger.info("Updating feedback for ID: {}", feedbackId);

		Feedback existingFeedback = feedbackrepo.findById(feedbackId).orElse(null);

		if (existingFeedback != null) {

			existingFeedback.setFeedbackText(feedbackDTO.getFeedbackText());
 
			// Set Indian Standard Time (IST)

			TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			timestamp.setTime(timestamp.getTime() + timeZone.getOffset(timestamp.getTime()));

			existingFeedback.setDateTime(timestamp);
 
			feedbackrepo.save(existingFeedback);

			return new ResponseEntity<>("Details updated", HttpStatus.OK);

		} else {

			logger.error("Feedback details not found for ID: {}", feedbackId);

			throw new FeedBackNotFound("Feedback details are not found for the Id: " + feedbackId);

		}

	}
 
	@Override

	public ResponseEntity<String> deleteFeedback(Integer feedbackId) throws Throwable {

		logger.info("Deleting feedback for ID: {}", feedbackId);

		Feedback feedback = feedbackrepo.findById(feedbackId).orElse(null);

		if (feedback != null) {

			feedbackrepo.deleteById(feedbackId);

			return new ResponseEntity<>("Feedback Deleted", HttpStatus.OK);

		} else {

			logger.error("Feedback details not found for ID: {}", feedbackId);

			throw new FeedBackNotFound("Feedback details are not found for the Id: " + feedbackId);

		}

	}
 
	@Override

	public ResponseEntity<FeedbackDto> getFeedback(Integer feedbackId) throws Throwable {

		logger.info("Fetching feedback for ID: {}", feedbackId);

		Feedback feedback = feedbackrepo.findById(feedbackId).orElse(null);

		if (feedback != null) {

			FeedbackDto feedbackDto = new FeedbackDto();

			feedbackDto.setFeedbackText(feedback.getFeedbackText());

			feedbackDto.setDateTime(feedback.getDateTime());

			feedbackDto.setPlaceId(feedback.getPlaceId());

			return new ResponseEntity<>(feedbackDto, HttpStatus.OK);

		} else {

			logger.error("Feedback details not found for ID: {}", feedbackId);

			throw new FeedBackNotFound("Feedback details are not found for the Id: " + feedbackId);

		}

	}
 
	@Override

	public ResponseEntity<String> getPlacefortour(Integer placeId, Integer userId, FeedbackDto feedbackdto)

			throws Throwable {

		logger.info("Getting place for tour. Place ID: {}, User ID: {}", placeId, userId);

		Integer placeId1 = placeClient.getPlacefortour(placeId);

		Integer userId1 = userclient.getUserForClient(userId);

		if (placeId1 != null && userId1 != null) {

			Feedback feedback = new Feedback();

			feedback.setFeedbackText(feedbackdto.getFeedbackText());

			feedback.setDateTime(feedbackdto.getDateTime());

			feedback.setPlaceId(placeId1);

			feedback.setUserId(userId1);

			feedbackrepo.save(feedback);

			return new ResponseEntity<>("Created", HttpStatus.OK);

		} else {

			logger.error("Invalid details for place and/or user. Place ID: {}, User ID: {}", placeId, userId);

			throw new FeedBackNotFound("Please check the details carefully before entering");

		}

	}
 
	@Override

	public ResponseEntity<UserDTO> getUserDTO(Integer feedbackId) throws Throwable {

		logger.info("Fetching user DTO for feedback ID: {}", feedbackId);

		Feedback feedback = feedbackrepo.findById(feedbackId).orElse(null);

		if (feedback != null) {

			Integer userId = feedback.getUserId();

			UserDTO userDto = userclient.getUserById(userId).getBody();

			return new ResponseEntity<>(userDto, HttpStatus.OK);

		} else {

			logger.error("Feedback not found with ID: {}", feedbackId);

			throw new FeedBackNotFound("Feedback not found with this id: " + feedbackId);

		}

	}
 
	@Override

	public ResponseEntity<List<FeedbackDto>> getFeedBackByUserId(Integer userId) throws Throwable {

		List<Feedback> feedback1 = feedbackrepo.findByUserId(userId);

		List<FeedbackDto> feedbackDto1 = new LinkedList<>();

		for (Feedback feedback : feedback1) {

			FeedbackDto feedbackDto = new FeedbackDto();

			feedbackDto.setPlaceId(feedback.getPlaceId());

			PlaceDTO placeDTO = placeClient.getPlaceById(feedback.getPlaceId()).getBody();

			feedbackDto.setPlaceName(placeDTO.getPlaceName());

			feedbackDto.setFeedbackText(feedback.getFeedbackText());

			feedbackDto.setDateTime(feedback.getDateTime());

			feedbackDto1.add(feedbackDto);

		}

		return new ResponseEntity<>(feedbackDto1, HttpStatus.OK);

	}

}
