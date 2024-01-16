package com.admin.entity;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Admin {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "full_name", nullable = false)
    @Size(max = 100)
    private String fullName;
 
   
    @Column(name = "mobile_number", nullable = false)
    @Size(max = 100)
    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number (must have exactly 10 digits)")
    private String mobileNumber;

    @Column(name = "address", nullable = false)
    @Size(max = 100)
    private String address;
 
    @Column(nullable = false)
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email address (must be @gmail.com)")
    private String email;
 
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()-+=]).{8,}$", message = "Invalid password")
    @Column(nullable = false)
    @Size(max = 100)
    private String password;
}