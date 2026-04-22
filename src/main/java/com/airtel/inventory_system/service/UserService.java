package com.airtel.inventory_system.service;

import java.util.List;
import java.util.Optional;

import com.airtel.inventory_system.dto.UserRegistrationDTO;
import com.airtel.inventory_system.entity.User;

public interface UserService {
    
    User createUser(UserRegistrationDTO userDTO);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    Optional<User> getUserById(Long id);
    
    Optional<User> getUserByUsername(String username);
    
    List<User> getAllUsers();
    
    List<User> getUsersByRole(String role);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    void updateLastLogin(String username);
    
    User getCurrentUser();
    
    boolean isAdmin();
    
    boolean isHR();
    
    boolean isTechnician();
}