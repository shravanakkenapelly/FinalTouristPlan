package com.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.entity.Admin;
import com.admin.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAdminById(@PathVariable(name = "id") Long id,@Valid @RequestBody Admin admin)
            throws Throwable {
        adminService.updateAdminById(id, admin);
        logger.info("Admin updated successfully. Admin ID: {}", id);
        return new ResponseEntity<>("Admin updated successfully", HttpStatus.OK);
    }

    @GetMapping("/viewbyid/{id}")
    public ResponseEntity<Admin> viewAdminById(@PathVariable(name = "id") Long id) throws Throwable {
        Admin admin = adminService.viewAdminById(id);
        logger.info("Viewing admin by ID: {}", id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<Admin>> viewAllAdmins() throws Throwable {
        List<Admin> admins = adminService.viewAllAdmins();
        logger.info("Viewing all admins");
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable(name = "id") Long id) throws Throwable {
        adminService.deleteAdminById(id);
        logger.info("Admin deleted successfully. Admin ID: {}", id);
        return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);
    }
}
