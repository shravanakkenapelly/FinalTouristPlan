package com.tourplan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourplan.dto.ListOfTour;
import com.tourplan.dto.TourDetailsDTO;
import com.tourplan.dto.TourPlanResponseDTO;
import com.tourplan.service.TourPlanService;

@RestController
@RequestMapping("/tourplan")
@CrossOrigin(origins = "*")	//Frontend Connection
public class TourPlanController {

    private static final Logger logger = LoggerFactory.getLogger(TourPlanController.class);

    @Autowired
    TourPlanService tourPlanService;

    @GetMapping("/gettourdetails/{tourId}")
    public ResponseEntity<TourPlanResponseDTO> getTourDetails(@PathVariable(name = "tourId") Integer tourId) throws Throwable {
        logger.info("Fetching tour details for tourId: {}", tourId);
        return tourPlanService.getTourPlan(tourId);
    }

    @PutMapping("/updateTour/{tourId}")
    public ResponseEntity<String> updateTour(@PathVariable(name = "tourId") Integer tourId,
                                            @RequestBody TourDetailsDTO tourDetailsDto) throws Throwable {
        logger.info("Updating tour details for tourId: {}", tourId);
        return tourPlanService.updateTour(tourId, tourDetailsDto);
    }

    @DeleteMapping("/delete/{tourId}")
    public ResponseEntity<String> deleteTour(@PathVariable(name = "tourId") Integer tourId) throws Throwable {
        logger.info("Deleting tour for tourId: {}", tourId);
        return tourPlanService.deleteTour(tourId);
    }

    @PostMapping("/addTour/{userId}")
    public ResponseEntity<String> plan(@PathVariable(name = "userId") Integer userId,
                                       @RequestBody TourDetailsDTO tourDetailsDto) {
        logger.info("Creating tour plan for userId: {}", userId);
        return tourPlanService.createPlan(userId, tourDetailsDto);
    }
    
    @GetMapping("/getTourByUser/{userId}")
    public ResponseEntity<List<ListOfTour>> getTourByUserId (@PathVariable(name = "userId") Integer userId)throws Throwable{
    	logger.info("Fetching tour details for tourId: {}", userId);
        return tourPlanService.getTourByUserId(userId);
    }
}
