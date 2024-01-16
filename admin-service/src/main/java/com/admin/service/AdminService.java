package com.admin.service;

import java.util.List;

import com.admin.dto.AdminDto;
import com.admin.dto.CredentialsDto;
import com.admin.dto.SignUpDto;
import com.admin.entity.Admin;

public interface AdminService {
	
	AdminDto login(CredentialsDto credentialsDto);
	
	AdminDto register(SignUpDto adminDto);
	
	AdminDto findByLoginEmail(String email);
	
	void updateAdminById(Long id, Admin admin) throws Throwable;

	Admin viewAdminById(Long id) throws Throwable;
	
	List<Admin> viewAllAdmins() throws Throwable;
	
	void deleteAdminById(Long id) throws Throwable;
	
}
