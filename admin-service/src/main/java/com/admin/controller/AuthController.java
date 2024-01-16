package com.admin.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.config.AdminAuthenticationProvider;
import com.admin.dto.AdminDto;
import com.admin.dto.CredentialsDto;
import com.admin.dto.SignUpDto;
import com.admin.serviceimpl.AdminServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AdminServiceImpl adminService;
    private final AdminAuthenticationProvider adminAuthenticationProvider;

    @PostMapping("/adminlogin")
    public ResponseEntity<AdminDto> login(@Valid @RequestBody CredentialsDto credentialsDto) {
        AdminDto userDto = adminService.login(credentialsDto);
        userDto.setToken(adminAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/adminregister")
    public ResponseEntity<AdminDto> register(@Valid @RequestBody SignUpDto admin) {
        AdminDto createdAdmin = adminService.register(admin);
        createdAdmin.setToken(adminAuthenticationProvider.createToken(createdAdmin));
        return ResponseEntity.created(URI.create("/admins/" + createdAdmin.getId())).body(createdAdmin);
    }

}
