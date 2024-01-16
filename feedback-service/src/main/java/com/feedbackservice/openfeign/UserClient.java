package com.feedbackservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.feedbackservice.dto.UserDTO;

@FeignClient("USER-SERVICE")
public interface UserClient {
	
	@GetMapping("user/getuserforclient/{userId}")
	public Integer getUserForClient (@PathVariable(name="userId") Integer userId);
	
	@GetMapping("user/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(name="userId") Integer userId);
	
	

}
