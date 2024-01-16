package com.tourplan.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
	
	private Integer userId;
	private String userName;
	private String userEmail;
	private String number;
	private String address;

}

