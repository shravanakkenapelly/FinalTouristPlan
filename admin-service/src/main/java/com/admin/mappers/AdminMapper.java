package com.admin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.admin.dto.AdminDto;
import com.admin.dto.SignUpDto;
import com.admin.entity.Admin;


@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDto toAdminDto(Admin user);

    @Mapping(target = "password", ignore = true)
    Admin signUpToAdmin(SignUpDto signUpDto);

}
