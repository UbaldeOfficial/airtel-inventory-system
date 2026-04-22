package com.airtel.inventory_system.controller.web;

import com.airtel.inventory_system.entity.User;
import com.airtel.inventory_system.service.AuditLogService;
import com.airtel.inventory_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuditLogService auditLogService;
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String logout,
                       @RequestParam(required = false) String expired,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        if (expired != null) {
            model.addAttribute("message", "Your session has expired. Please login again");
        }
        
        return "auth/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String username = auth.getName();
            auditLogService.logLogout(username);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
    
    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "auth/profile";
    }
}