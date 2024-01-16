package com.capgemini.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.dto.UserDto;
import com.capgemini.entity.User;
import com.capgemini.repository.UserRepository;

@Component
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepo;

	public UserDto findUserById(Integer userId) {
		logger.info("Finding user by ID: {}", userId);
		Optional<User> userOptional = userRepo.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			UserDto userDTO = new UserDto();
			userDTO.setId(user.getId());
			userDTO.setFullName(user.getFullName());
			userDTO.setEmail(user.getEmail());
			userDTO.setMobileNumber(user.getMobileNumber());
			userDTO.setAddress(user.getAddress());
			return userDTO;
		} else {
			logger.warn("User with ID {} not found", userId);
			return null;
		}
	}

	public UserDto updateUser(Integer userId, UserDto userDTO) {
		logger.info("Updating user with ID: {}", userId);
		Optional<User> optionalUser = userRepo.findById(userId);
		if (optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			existingUser.setFullName(userDTO.getFullName());
			existingUser.setEmail(userDTO.getEmail());
			existingUser.setAddress(userDTO.getAddress());
			existingUser.setMobileNumber(userDTO.getMobileNumber());
			userRepo.save(existingUser);
			return userDTO;
		} else {
			logger.warn("User with ID {} not found for update", userId);
			return null;
		}
	}

	public List<UserDto> getAllUsers() {
		logger.info("Fetching all users");
		List<User> users = userRepo.findAll();
		List<UserDto> userDTOs = new ArrayList<>();

		for (User user : users) {
			UserDto userDTO = new UserDto();
			userDTO.setId(user.getId());
			userDTO.setFullName(user.getFullName());
			userDTO.setEmail(user.getEmail());
			userDTO.setMobileNumber(user.getMobileNumber());
			userDTO.setAddress(user.getAddress());
			userDTO.setPassword(user.getPassword());
			userDTOs.add(userDTO);
		}

		return userDTOs;
	}

	public UserDto findUserByName(String userName) {
		logger.info("Finding user by name: {}", userName);
		Optional<User> userOptional = userRepo.findByFullName(userName);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			UserDto userDTO = new UserDto();
			userDTO.setId(user.getId());
			userDTO.setFullName(user.getFullName());
			userDTO.setEmail(user.getEmail());
			userDTO.setMobileNumber(user.getMobileNumber());
			userDTO.setAddress(user.getAddress());
			return userDTO;
		} else {
			logger.warn("User with name {} not found", userName);
			return null;
		}
	}

	public Integer registerUser(UserDto userDTO) {
//		logger.info("Registering user: {}", userDTO.getFullName());
		Optional<User> optionalUser  = userRepo.findByEmail(userDTO.getEmail());
		if(optionalUser.isPresent()) {
			System.out.println("Not good");
			return 0;
		}
		else {
			User user = new User();
			user.setFullName(userDTO.getFullName());
			user.setAddress(userDTO.getAddress());
			user.setEmail(userDTO.getEmail());
			user.setMobileNumber(userDTO.getMobileNumber());
			user.setPassword(userDTO.getPassword());
			user.setId(userDTO.getId());

			userRepo.save(user);
			System.out.println("goosd");
			return 1;
			
		}
		

	}

	public Integer getUserForClient(Integer userId) {
		logger.info("Fetching user for client with ID: {}", userId);
		User user = userRepo.findById(userId).orElseThrow();
		return user.getId();
	}
}
