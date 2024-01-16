package com.admin;
 
import static org.mockito.Mockito.*;
 
import java.util.ArrayList;

import java.util.List;

import java.util.Optional;
 
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;
 
import com.admin.controller.AdminController;

import com.admin.entity.Admin;

import com.admin.exceptions.AdminException;

import com.admin.mappers.AdminMapper;

import com.admin.repository.AdminRepository;

import com.admin.serviceimpl.AdminServiceImpl;
 
import static org.junit.jupiter.api.Assertions.*;
 
@ExtendWith(MockitoExtension.class)
class AdminControllerAndServiceTest {
 
    @Mock

    private AdminRepository adminRepository;
 
    @Mock

    private PasswordEncoder passwordEncoder;
 
    @Mock

    private AdminMapper adminMapper;
 
    @InjectMocks

    private AdminServiceImpl adminService;
 
    @InjectMocks

    private AdminController adminController;
 
    @Test

    void testUpdateAdminById() throws Throwable {

        Long adminId = 1L;

        Admin existingAdmin = new Admin();

        Admin updatedAdmin = new Admin();

        updatedAdmin.setFullName("Updated Name");
 
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));

        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");

        when(adminRepository.save(existingAdmin)).thenReturn(updatedAdmin);
 
        adminService.updateAdminById(adminId, updatedAdmin);

        assertEquals("Updated Name", existingAdmin.getFullName());

    }
 
    @Test

    void testViewAdminById() throws Throwable {

        Long adminId = 1L;

        Admin existingAdmin = new Admin();
 
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
 
        Admin result = adminService.viewAdminById(adminId);

        assertNotNull(result);

    }
 
    @Test

    void testViewAdminById_UnknownAdmin() {

        Long adminId = 1L;
 
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(AdminException.class, () -> adminService.viewAdminById(adminId));

    }
 
    @Test

    void testViewAllAdmins() throws Throwable {

        List<Admin> admins = new ArrayList<>();

        admins.add(new Admin());
 
        when(adminRepository.findAll()).thenReturn(admins);
 

        List<Admin> result = adminService.viewAllAdmins();
 
        assertNotNull(result);

        assertFalse(result.isEmpty());

    }
 
    @Test

    void testViewAllAdmins_NoAdminsFound() {


        when(adminRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(AdminException.class, () -> adminService.viewAllAdmins());

    }
 
    @Test

    void testDeleteAdminById() throws Throwable {

        Long adminId = 1L;

        Admin existingAdmin = new Admin();
 
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));

        adminService.deleteAdminById(adminId);

        verify(adminRepository, times(1)).delete(existingAdmin);

    }
 
    @Test

    void testDeleteAdminById_UnknownAdmin() {

        Long adminId = 1L;
 
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(AdminException.class, () -> adminService.deleteAdminById(adminId));

    }

}
