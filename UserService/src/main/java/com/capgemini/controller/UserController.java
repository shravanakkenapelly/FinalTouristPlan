package com.capgemini.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.dto.UserDto;
import com.capgemini.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Integer addCustomer(@Valid @RequestBody UserDto userDTO) {
        logger.info("Registering user: {}", userDTO.getFullName());
        return userService.registerUser(userDTO);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "userId") Integer userId) {
        logger.info("Fetching user by ID: {}", userId);
        UserDto user = userService.findUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            logger.warn("User with ID {} not found", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getbyname/{userName}")
    public ResponseEntity<Integer> getUserByName(@PathVariable String userName) {
        logger.info("Fetching user by name: {}", userName);
        UserDto user = userService.findUserByName(userName);
        if (user != null) {
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } else {
            logger.warn("User with name {} not found", userName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("Fetching all users");
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDTO) {
        logger.info("Updating user with ID: {}", userId);
        UserDto updatedUser = userService.updateUser(userId, userDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            logger.warn("User with ID {} not found for update", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getuserforclient/{userId}")
    public Integer getUserForClient(@PathVariable(name = "userId") Integer userId) {
        logger.info("Fetching user for client with ID: {}", userId);
        return userService.getUserForClient(userId);
    }
}
