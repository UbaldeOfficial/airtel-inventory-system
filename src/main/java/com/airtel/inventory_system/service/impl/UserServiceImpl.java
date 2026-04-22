package com.airtel.inventory_system.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airtel.inventory_system.dto.UserRegistrationDTO;
import com.airtel.inventory_system.entity.User;
import com.airtel.inventory_system.exception.DuplicateResourceException;
import com.airtel.inventory_system.exception.ResourceNotFoundException;
import com.airtel.inventory_system.repository.UserRepository;
import com.airtel.inventory_system.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public User createUser(UserRegistrationDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + userDTO.getUsername());
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + userDTO.getEmail());
        }
        
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setDepartment(userDTO.getDepartment());
        user.setPhone(userDTO.getPhone());
        
        Set<String> roles = new HashSet<>();
        roles.add(userDTO.getRole());
        user.setRoles(roles);
        
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setDepartment(userDetails.getDepartment());
        user.setPhone(userDetails.getPhone());
        user.setEnabled(userDetails.isEnabled());
        
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setEnabled(false);
        userRepository.save(user);
    }
    
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findUsersByRole(role);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public void updateLastLogin(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }
    
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username).orElse(null);
            } else if (principal instanceof String) {
                return userRepository.findByUsername((String) principal).orElse(null);
            }
        }
        return null;
    }
    
    @Override
    public boolean isAdmin() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getRoles().contains(User.ROLE_ADMIN);
    }
    
    @Override
    public boolean isHR() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getRoles().contains(User.ROLE_HR);
    }
    
    @Override
    public boolean isTechnician() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getRoles().contains(User.ROLE_TECHNICIAN);
    }
}