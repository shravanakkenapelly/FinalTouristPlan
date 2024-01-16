package com.tourplan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tourId;
	
	@NotNull(message= "user id should not be null")
	private Integer userId;


}
