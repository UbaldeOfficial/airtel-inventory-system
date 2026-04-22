package com.airtel.inventory_system.config;

import com.airtel.inventory_system.entity.User;
import com.airtel.inventory_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        createDefaultUsers();
    }
    
    private void createDefaultUsers() {
        // Create ADMIN user
        if (!userRepository.existsByUsername("UbaldeAdmin")) {
            User admin = new User();
            admin.setUsername("UbaldeAdmin");
            admin.setEmail("admin@airtel.com");
            admin.setPassword(passwordEncoder.encode("AdminUbalde123"));
            admin.setFullName("Ubalde Administrator");
            admin.setDepartment("IT Administration");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_ADMIN);
            admin.setRoles(roles);
            
            userRepository.save(admin);
            System.out.println("✅ ADMIN user created: UbaldeAdmin / AdminUbalde123");
        }
        
        // Create HR user
        if (!userRepository.existsByUsername("Alinehr")) {
            User hr = new User();
            hr.setUsername("Alinehr");
            hr.setEmail("hr@airtel.com");
            hr.setPassword(passwordEncoder.encode("Alinehr123"));
            hr.setFullName("Aline Human Resources");
            hr.setDepartment("Human Resources");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_HR);
            hr.setRoles(roles);
            
            userRepository.save(hr);
            System.out.println("✅ HR user created: Alinehr / Alinehr123");
        }
        
        // Create TECHNICIAN user
        if (!userRepository.existsByUsername("UbaldeTech")) {
            User tech = new User();
            tech.setUsername("UbaldeTech");
            tech.setEmail("tech@airtel.com");
            tech.setPassword(passwordEncoder.encode("Alinetech123"));
            tech.setFullName("Ubalde Technician");
            tech.setDepartment("IT Support");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_TECHNICIAN);
            tech.setRoles(roles);
            
            userRepository.save(tech);
            System.out.println("✅ TECHNICIAN user created: UbaldeTech / Alinetech123");
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  DEFAULT USERS READY FOR LOGIN");
        System.out.println("═══════════════════════════════════════════════════════");
    }
}