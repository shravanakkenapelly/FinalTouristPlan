
package com.tourplan.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tourplan.dto.PlaceDTO;

@FeignClient("PLACE-SERVICE")
public interface PlaceClient {

	@PostMapping("place/getListOfPlacedtoById")
	public ResponseEntity<List<PlaceDTO>> getListOfPlaceDTOByPlaceId(@RequestBody List<Integer> placeIds);

	@PostMapping("place/getListOfPlaceIds")
	public ResponseEntity<List<Integer>> getListOfPlaceIds(@RequestBody List<Integer> placeIds);
}
