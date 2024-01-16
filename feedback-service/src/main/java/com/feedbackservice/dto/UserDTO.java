package com.feedbackservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

	private Integer userId;
	private String userName;
//	private String password;
	private String userEmail;
	private String number;
	private String address;
	private String role;

}
