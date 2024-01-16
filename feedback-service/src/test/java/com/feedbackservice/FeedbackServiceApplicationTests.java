package com.feedbackservice;
import com.feedbackservice.advices.FeedBackNotFound;
import com.feedbackservice.dto.FeedbackDto;
import com.feedbackservice.dto.UserDTO;
import com.feedbackservice.entity.Feedback;
import com.feedbackservice.openfeign.PlaceClient;
import com.feedbackservice.openfeign.UserClient;
import com.feedbackservice.repository.FeedbackRepository;
import com.feedbackservice.serviceimpl.FeedbackServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private PlaceClient placeClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback existingFeedback;

    @BeforeEach
    void setUp() {
        existingFeedback = new Feedback();
        existingFeedback.setPlaceId(1);
        existingFeedback.setFeedbackText("Good feedback");
        existingFeedback.setDateTime(new Timestamp(System.currentTimeMillis()));
        existingFeedback.setPlaceId(101);
        existingFeedback.setUserId(201);
    }

    @Test
    void testGetAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackList.add(existingFeedback);

        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        ResponseEntity<List<Feedback>> responseEntity = feedbackService.getAllFeedback();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(feedbackList, responseEntity.getBody());

        verify(feedbackRepository, times(1)).findAll();
    }

    @Test
    void testUpdateFeedback() throws Throwable {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setFeedbackText("Updated feedback");

        when(feedbackRepository.findById(1)).thenReturn(Optional.of(existingFeedback));

        ResponseEntity<String> responseEntity = feedbackService.updateFeedback(1, feedbackDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Details updated", responseEntity.getBody());

        verify(feedbackRepository, times(1)).findById(1);
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testUpdateFeedbackNotFound() {
        when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(FeedBackNotFound.class, () -> {
            feedbackService.updateFeedback(1, new FeedbackDto());
        });

        verify(feedbackRepository, times(1)).findById(1);
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void testDeleteFeedback() throws Throwable {
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(existingFeedback));

        ResponseEntity<String> responseEntity = feedbackService.deleteFeedback(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Feedback Deleted", responseEntity.getBody());

        verify(feedbackRepository, times(1)).findById(1);
        verify(feedbackRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFeedbackNotFound() {
        when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(FeedBackNotFound.class, () -> {
            feedbackService.deleteFeedback(1);
        });

        verify(feedbackRepository, times(1)).findById(1);
        verify(feedbackRepository, never()).deleteById(anyInt());
    }

    @Test
    void testGetFeedback() throws Throwable {
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(existingFeedback));

        ResponseEntity<FeedbackDto> responseEntity = feedbackService.getFeedback(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(feedbackRepository, times(1)).findById(1);
    }

    @Test
    void testGetFeedbackNotFound() {
        when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(FeedBackNotFound.class, () -> {
            feedbackService.getFeedback(1);
        });

        verify(feedbackRepository, times(1)).findById(1);
    }

    @Test
    void testGetPlacefortour() throws Throwable {
        when(placeClient.getPlacefortour(101)).thenReturn(101);
        when(userClient.getUserForClient(201)).thenReturn(201);

        ResponseEntity<String> responseEntity = feedbackService.getPlacefortour(101, 201, new FeedbackDto());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Created", responseEntity.getBody());

        verify(placeClient, times(1)).getPlacefortour(101);
        verify(userClient, times(1)).getUserForClient(201);
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testGetPlacefortourInvalidDetails() {
        when(placeClient.getPlacefortour(101)).thenReturn(null);
        when(userClient.getUserForClient(201)).thenReturn(null);

        assertThrows(FeedBackNotFound.class, () -> {
            feedbackService.getPlacefortour(101, 201, new FeedbackDto());
        });

        verify(placeClient, times(1)).getPlacefortour(101);
        verify(userClient, times(1)).getUserForClient(201);
        verify(feedbackRepository, never()).save(any(Feedback.class));
    }

    @Test
    void testGetUserDTO() throws Throwable {
        when(feedbackRepository.findById(1)).thenReturn(Optional.of(existingFeedback));
        when(userClient.getUserById(201)).thenReturn(ResponseEntity.ok(new UserDTO()));

        ResponseEntity<UserDTO> responseEntity = feedbackService.getUserDTO(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(feedbackRepository, times(1)).findById(1);
        verify(userClient, times(1)).getUserById(201);
    }

    @Test
    void testGetUserDTONotFound() {
        when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(FeedBackNotFound.class, () -> {
            feedbackService.getUserDTO(1);
        });

        verify(feedbackRepository, times(1)).findById(1);
        verify(userClient, never()).getUserById(anyInt());
    }
}
