package com.tourplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class TourDatesPlaces {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	
	@NotNull(message  = "tour id should not be null")
	private Integer tourId;
	@NotNull(message  = "tour date should not be null")
	private String tourDate;
	@NotNull(message  = "place id should not be null")
	private Integer placeId;

}
