package com.placeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer placeId;
	 @Column(name = "place_name", nullable = false)
	    @Size(max = 100)
	    private String placeName;

	   @NotNull
	    private String images;

	    @NotNull 
	    @Size(max = 100)
	    private String address;

	    @NotNull
	    @Size(max = 100)
	    private String area;

	    @NotNull
	    @PositiveOrZero(message = "Distance must be a positive number or zero")
	    private Double distance;

	    @NotNull
	    @Size(max = 255)
	    private String description;

	    @NotNull
	    @Size(max = 255)
	    private String tags;


}
