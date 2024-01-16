package com.admin.serviceimpl;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.admin.controller.AuthController;
import com.admin.dto.AdminDto;
import com.admin.dto.CredentialsDto;
import com.admin.dto.SignUpDto;
import com.admin.entity.Admin;
import com.admin.exceptions.AdminException;
import com.admin.exceptions.AppException;
import com.admin.mappers.AdminMapper;
import com.admin.repository.AdminRepository;
import com.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;

    @Override
    public AdminDto login(CredentialsDto credentialsDto) {
        Admin admin = adminRepo.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unknown admin", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), admin.getPassword())) {
            logger.info("Login successful for user: {}", admin.getEmail());
            return adminMapper.toAdminDto(admin);
        }
        logger.error("Invalid password for user: {}", admin.getEmail());
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public AdminDto register(SignUpDto adminDto) {
        Optional<Admin> optionalAdmin = adminRepo.findByEmail(adminDto.email());

        if (optionalAdmin.isPresent()) {
            logger.error("Login already exists for email: {}", adminDto.email());
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Admin admin = adminMapper.signUpToAdmin(adminDto);
        admin.setPassword(passwordEncoder.encode(CharBuffer.wrap(adminDto.password())));

        Admin savedAdmin = adminRepo.save(admin);

        logger.info("Registration successful for user: {}", savedAdmin.getEmail());
        return adminMapper.toAdminDto(savedAdmin);
    }

    @Override
    public AdminDto findByLoginEmail(String email) {
        Admin admin = adminRepo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Unknown admin with email: {}", email);
                    return new AppException("Unknown Admin", HttpStatus.NOT_FOUND);
                });

        logger.info("Found admin by email: {}", email);
        return adminMapper.toAdminDto(admin);
    }

    @Override
    public void updateAdminById(Long id, Admin admin) throws Throwable {
        Admin existingAdmin = adminRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Admin not found with ID: {}", id);
                    return new AdminException("Admin not found with ID: " + id);
                });

        existingAdmin.setFullName(admin.getFullName());
        existingAdmin.setMobileNumber(admin.getMobileNumber());
        existingAdmin.setAddress(admin.getAddress());
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));

        adminRepo.save(existingAdmin);
        logger.info("Admin updated successfully. Admin ID: {}", id);
    }

    @Override
    public Admin viewAdminById(Long id) throws Throwable {
        Admin admin = adminRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Admin not found with ID: {}", id);
                    return new AdminException("Admin not found with ID: " + id);
                });

        logger.info("Viewing admin by ID: {}", id);
        return admin;
    }

    @Override
    public List<Admin> viewAllAdmins() throws Throwable {
        List<Admin> admins = adminRepo.findAll();

        if (admins.isEmpty()) {
            logger.error("No admins found");
            throw new AdminException("No admins found");
        }

        logger.info("Viewing all admins");
        return admins;
    }

    @Override
    public void deleteAdminById(Long id) throws Throwable {
        Admin adminToDelete = adminRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Admin not found with ID: {}", id);
                    return new AdminException("Admin not found with ID: " + id);
                });

        adminRepo.delete(adminToDelete);
        logger.info("Admin deleted successfully. Admin ID: {}", id);
    }
}
