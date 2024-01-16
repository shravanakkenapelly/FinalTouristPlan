package com.placeservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.placeservice.dto.PlaceDTO;
import com.placeservice.service.PlaceService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class PlaceController {

    private static final Logger logger = LogManager.getLogger(PlaceController.class);

    @Autowired
    public PlaceService placeservice;

    @PostMapping("/addplace")
    public ResponseEntity<String> addPlace(@RequestBody PlaceDTO placeDto) {
        logger.info("Received request to add a place with PlaceDTO: {}", placeDto);
        return placeservice.addPlace(placeDto);
    }

    @GetMapping("/getallplaces")
    public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
        logger.info("Received request to get all places.");
        return placeservice.getAllPlaces();
    }

    @GetMapping("/getplacebyid/{placeId}")
    public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable(name = "placeId") Integer placeId) throws Throwable {
        logger.info("Received request to get place by ID: {}", placeId);
        return placeservice.getPlaceById(placeId);
    }

    @GetMapping("/getplacebyname/{placeName}")
    public ResponseEntity<List<PlaceDTO>> getPlaceByName(@PathVariable(name = "placeName") String placeName)
            throws Throwable {
        logger.info("Received request to get place by name: {}", placeName);
        return placeservice.getPlaceByName(placeName);
    }

    @GetMapping("/getplacebytags/{tags}")
    public ResponseEntity<PlaceDTO> getPlaceByTags(@PathVariable(name = "tags") String tags) throws Throwable {
        logger.info("Received request to get place by tags: {}", tags);
        return placeservice.getPlaceByTags(tags);
    }

    @PutMapping("/updateplaces/")
    public ResponseEntity<String> updatePlaces(@RequestBody PlaceDTO placeDto) throws Throwable {
        logger.info("Received request to update place with PlaceDTO: {}", placeDto);
        return placeservice.updatePlaces(placeDto);
    }

    @DeleteMapping("/deletePlaceByName/{placeName}")
    public ResponseEntity<String> deletePlaceByName(@PathVariable(name = "placeName") String placeName) throws Throwable {
        logger.info("Received request to delete place by name: {}", placeName);
        return placeservice.deletePlaceByPlaceName(placeName);
    }

    @DeleteMapping("/deletePlaceByPlaceId")
    public ResponseEntity<String> deletePlaceById(@RequestParam Integer placeId) throws Throwable {
        logger.info("Received request to delete place by ID: {}", placeId);
        return placeservice.deletePlaceById(placeId);
    }

    @GetMapping("/place/getplacefortour/{placeId}")
    public Integer getPlacefortour(@PathVariable(name = "placeId") Integer placeId) throws Throwable {
        logger.info("Received request to get place for tour by ID: {}", placeId);
        return placeservice.getPlacefortour(placeId);
    }

    @PostMapping("/place/getListOfPlacedtoById")
    public ResponseEntity<List<PlaceDTO>> getListOfPlaceDTOByPlaceId(@RequestBody List<Integer> placeIds)
            throws Throwable {
        logger.info("Received request to get list of PlaceDTOs by IDs: {}", placeIds);
        return placeservice.getListOfPlaceDTOByPlaceId(placeIds);
    }

    @PostMapping("/place/getListOfPlaceIds")
    public ResponseEntity<List<Integer>> getListOfPlaceIds(@RequestBody List<Integer> placeIds) throws Throwable {
        logger.info("Received request to get list of place IDs: {}", placeIds);
        return placeservice.getListOfPlaceIdsl(placeIds);
    }

    @PostMapping("/{placeId}/upload-image")
    public String handleImageUpload(
            @PathVariable int placeId,
            @RequestParam("image") MultipartFile image) {

        if (image != null) {
            try {
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                String imagePath = "C:/Users/SHAIAHAM/Pictures/ProjectPlacePics/" + filename;
                File imageFile = new File(imagePath);

                image.transferTo(imageFile);
                placeservice.linkImageToPlace(placeId, imagePath);

                logger.info("Image uploaded successfully for place ID: {}", placeId);
                return "Image uploaded successfully";
            } catch (IOException e) {
                logger.error("Failed to upload image for place ID: {}", placeId, e);
                return "Failed to upload image";
            }
        } else {
            logger.warn("No image file provided for place ID: {}", placeId);
            return "No image file provided";
        }
    }
}



