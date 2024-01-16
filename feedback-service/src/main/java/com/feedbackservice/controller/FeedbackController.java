package com.feedbackservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.feedbackservice.dto.FeedbackDto;
import com.feedbackservice.dto.UserDTO;
import com.feedbackservice.entity.Feedback;
import com.feedbackservice.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("getfeedbackbyId/{feedbackId}")
    public ResponseEntity<FeedbackDto> getFeedback(@PathVariable(name = "feedbackId") Integer feedbackId) {
        logger.info("Fetching feedback for ID: {}", feedbackId);
        try {
            return feedbackService.getFeedback(feedbackId);
        } catch (Throwable e) {
            logger.error("Error while fetching feedback for ID: {}", feedbackId, e);
           
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("getallfeedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        logger.info("Fetching all feedback");
        return feedbackService.getAllFeedback();
    }
    
    
    @PutMapping("updatefeedback/{feedbackId}")
    public ResponseEntity<String> updateFeedback(@PathVariable(name = "feedbackId") Integer feedbackId,
    		@Valid  @RequestBody FeedbackDto feedbackDTO) {
        logger.info("Updating feedback for ID: {}", feedbackId);
        try {
            return feedbackService.updateFeedback(feedbackId, feedbackDTO);
        } catch (Throwable e) {
            logger.error("Error while updating feedback for ID: {}", feedbackId, e);
           
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("deletefeedback/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable(name = "feedbackId") Integer feedbackId) {
        logger.info("Deleting feedback for ID: {}", feedbackId);
        try {
            return feedbackService.deleteFeedback(feedbackId);
        } catch (Throwable e) {
            logger.error("Error while deleting feedback for ID: {}", feedbackId, e);
           
            return ResponseEntity.status(500).build();
        }
    }
    
    
  
    @PostMapping("/addFeedback/{placeId}/{userId}")
    public ResponseEntity<String> addFeedback(@PathVariable(name = "placeId") Integer placeId,
                                              @PathVariable(name = "userId") Integer userId,
                                              @Valid  @RequestBody FeedbackDto feedbackdto) {
        logger.info("Adding feedback for place ID: {} and user ID: {}", placeId, userId);
        try {
            return feedbackService.getPlacefortour(placeId, userId, feedbackdto);
        } catch (Throwable e) {
            logger.error("Error while adding feedback for place ID: {} and user ID: {}", placeId, userId, e);
           
            return ResponseEntity.status(500).build();
        }
    }
    
    
  
    @PostMapping("/getuserdto/{feedbackId}")
    public ResponseEntity<UserDTO> getUserDTO(@PathVariable(name = "feedbackId") Integer feedbackId) {
        logger.info("Fetching user DTO for feedback ID: {}", feedbackId);
        try {
            return feedbackService.getUserDTO(feedbackId);
        } catch (Throwable e) {
            logger.error("Error while fetching user DTO for feedback ID: {}", feedbackId, e);
                       return ResponseEntity.status(500).build();
        }
    }
    
    @GetMapping("/getfeedbackbyuserId/{userId}")
        public ResponseEntity<List<FeedbackDto>> getFeedBackByUserId (@PathVariable(name= "userId") Integer userId) throws Throwable{
        	return feedbackService.getFeedBackByUserId(userId);
        }
}
