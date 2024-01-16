package com.capgemini.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    
    Optional<User> findByFullName(String username);
    Optional<User> findByEmail(String email);
}
