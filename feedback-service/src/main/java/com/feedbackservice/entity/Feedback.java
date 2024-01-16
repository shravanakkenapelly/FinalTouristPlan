package com.feedbackservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @NotNull(message = "Feedback text cannot be blank")
    @Size(max = 200, message = "Feedback text must be at most 200 characters")
    private String feedbackText;
    
    
    private Timestamp dateTime;
    
//    @Column (name = "user_Id")
    private Integer userId;
    
    private Integer placeId;
}
