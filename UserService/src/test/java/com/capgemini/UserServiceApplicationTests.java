package com.capgemini;



import com.capgemini.dto.UserDto;
import com.capgemini.entity.User;
import com.capgemini.repository.UserRepository;
import com.capgemini.services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindUserById() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setMobileNumber("1234567890");
        user.setAddress("123 Street, City");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        UserDto result = userService.findUserById(userId);
        
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFullName(), result.getFullName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getMobileNumber(), result.getMobileNumber());
        assertEquals(user.getAddress(), result.getAddress());
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setFullName("Updated Name");
        userDto.setEmail("updated@example.com");
        userDto.setMobileNumber("9876543210");
        userDto.setAddress("456 Avenue, Town");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFullName("John Doe");
        existingUser.setEmail("john@example.com");
        existingUser.setMobileNumber("1234567890");
        existingUser.setAddress("123 Street, City");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserDto updatedUser = userService.updateUser(userId, userDto);

        assertEquals(userDto.getId(), updatedUser.getId());
        assertEquals(userDto.getFullName(), updatedUser.getFullName());
        assertEquals(userDto.getEmail(), updatedUser.getEmail());
        assertEquals(userDto.getMobileNumber(), updatedUser.getMobileNumber());
        assertEquals(userDto.getAddress(), updatedUser.getAddress());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

	/*
	 * @Test void testAddCustomer() { // Create a UserDto for the test UserDto
	 * userDto = new UserDto(); userDto.setFullName("John Doe");
	 * userDto.setEmail("john@example.com"); userDto.setMobileNumber("1234567890");
	 * userDto.setAddress("123 Street, City"); userDto.setPassword("password123");
	 * when(userRepository.save(any(User.class))).thenReturn(new User()); UserDto
	 * result = userService.registerUser(userDto); verify(userRepository,
	 * times(1)).save(any(User.class)); assertEquals(userDto.getFullName(),
	 * result.getFullName()); assertEquals(userDto.getEmail(), result.getEmail());
	 * assertEquals(userDto.getMobileNumber(), result.getMobileNumber());
	 * assertEquals(userDto.getAddress(), result.getAddress());
	 * assertEquals(userDto.getPassword(), result.getPassword()); }
	 */
    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "John Doe", "john@example.com", "1234567890", "123 Street, City", "password1"));
        users.add(new User(2, "Jane Smith", "jane@example.com", "9876543210", "456 Avenue, Town", "password2"));
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> result = userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getFullName(), result.get(0).getFullName());
        assertEquals(users.get(1).getFullName(), result.get(1).getFullName());
    }


    // Other test cases for UserService methods...
}
