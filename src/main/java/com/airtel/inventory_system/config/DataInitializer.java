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
        // Create ADMIN user (SysAdmin)
        if (!userRepository.existsByUsername("24RP05770")) {
            User admin = new User();
            admin.setUsername("24RP05770");
            admin.setEmail("ibyimanikora@airtel.com");
            admin.setPassword(passwordEncoder.encode("24RP03478"));
            admin.setFullName("IBYIMANIKORA Ubalde - System Administrator");
            admin.setDepartment("IT Administration");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_ADMIN);
            admin.setRoles(roles);
            
            userRepository.save(admin);
            System.out.println("✅ ADMIN user created: 24RP05770 / 24RP03478");
        }
        
        // Create HR user
        if (!userRepository.existsByUsername("24RP03478")) {
            User hr = new User();
            hr.setUsername("24RP03478");
            hr.setEmail("aline@airtel.com");
            hr.setPassword(passwordEncoder.encode("Aline123"));
            hr.setFullName("CYUZUZO Aline - Human Resources");
            hr.setDepartment("Human Resources");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_HR);
            hr.setRoles(roles);
            
            userRepository.save(hr);
            System.out.println("✅ HR user created: 24RP03478 / Aline123");
        }
        
        // Create TECHNICIAN user
        if (!userRepository.existsByUsername("24RP05770tech")) {
            User tech = new User();
            tech.setUsername("24RP05770tech");
            tech.setEmail("tech@airtel.com");
            tech.setPassword(passwordEncoder.encode("UbaldeTech123"));
            tech.setFullName("IBYIMANIKORA Ubalde - Technician");
            tech.setDepartment("IT Support");
            
            Set<String> roles = new HashSet<>();
            roles.add(User.ROLE_TECHNICIAN);
            tech.setRoles(roles);
            
            userRepository.save(tech);
            System.out.println("✅ TECHNICIAN user created: 24RP05770tech / UbaldeTech123");
        }
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  DEFAULT USERS READY FOR LOGIN");
        System.out.println("═══════════════════════════════════════════════════════");
    }
}