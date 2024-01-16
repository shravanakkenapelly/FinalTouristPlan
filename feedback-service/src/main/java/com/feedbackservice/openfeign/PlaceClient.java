package com.feedbackservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.feedbackservice.dto.PlaceDTO;

@FeignClient("PLACE-SERVICE")
public interface PlaceClient {

	@GetMapping("place/getplacefortour/{placeId}")
	public Integer getPlacefortour(@PathVariable(name="placeId") Integer placeId);

	@GetMapping("/getplacebyid/{placeId}")
	public ResponseEntity<PlaceDTO> getPlaceById(@PathVariable(name = "placeId") Integer placeId) throws Throwable;
}
