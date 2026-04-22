package com.airtel.inventory_system.controller.web;

import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.entity.AuditLog;
import com.airtel.inventory_system.entity.User;
import com.airtel.inventory_system.service.AssetService;
import com.airtel.inventory_system.service.AssignmentService;
import com.airtel.inventory_system.service.AuditLogService;
import com.airtel.inventory_system.service.EmployeeService;
import com.airtel.inventory_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private AuditLogService auditLogService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Get current user info
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("username", authentication.getName());
        
        // Get statistics
        Map<String, Long> assetStats = assetService.getAssetStatistics();
        Map<String, Long> assignmentStats = assignmentService.getAssignmentStatistics();
        
        model.addAttribute("totalAssets", assetStats.get("total"));
        model.addAttribute("availableAssets", assetStats.get("available"));
        model.addAttribute("assignedAssets", assetStats.get("assigned"));
        model.addAttribute("damagedAssets", assetStats.get("damaged"));
        model.addAttribute("maintenanceAssets", assetStats.get("maintenance"));
        
        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());
        model.addAttribute("activeAssignments", assignmentStats.get("active"));
        model.addAttribute("overdueAssignments", assignmentStats.get("overdue"));
        
        // Get recent activities
        List<AuditLog> recentLogs = auditLogService.getRecentLogs(10);
        System.out.println("Recent logs count: " + recentLogs.size());
        model.addAttribute("recentLogs", recentLogs);
        
        // Get assets due for maintenance
        List<Asset> maintenanceAssets = assetService.getAssetsDueForMaintenance();
        model.addAttribute("maintenanceAssets", maintenanceAssets);
        
        return "dashboard";
    }
    
    @GetMapping("/api/dashboard/stats")
    @ResponseBody
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        Map<String, Long> assetStats = assetService.getAssetStatistics();
        stats.put("assetStats", assetStats);
        stats.put("chartData", new HashMap<>());
        
        return stats;
    }
}