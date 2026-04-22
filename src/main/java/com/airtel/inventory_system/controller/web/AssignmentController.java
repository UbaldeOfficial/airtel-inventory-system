package com.airtel.inventory_system.controller.web;

import com.airtel.inventory_system.dto.AssignmentDTO;
import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.entity.Assignment;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.service.AssetService;
import com.airtel.inventory_system.service.AssignmentService;
import com.airtel.inventory_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/assignments")
@PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'HR')")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    public String listAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("totalAssignments", assignments.size());
        model.addAttribute("activeCount", assignmentService.countActiveAssignments());
        model.addAttribute("overdueCount", assignmentService.countOverdueAssignments());
        return "assignments/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAssignedDate(LocalDate.now());
        
        // Get available assets
        List<Asset> availableAssets = assetService.getAssetsByStatus(Asset.AssetStatus.AVAILABLE);
        
        // Get active employees
        List<Employee> activeEmployees = employeeService.getActiveEmployees();
        
        System.out.println("Available Assets: " + availableAssets.size());
        System.out.println("Active Employees: " + activeEmployees.size());
        
        model.addAttribute("assignmentDTO", assignmentDTO);
        model.addAttribute("assets", availableAssets);
        model.addAttribute("employees", activeEmployees);
        
        return "assignments/form";
    }
    
    @PostMapping("/create")
    public String createAssignment(@ModelAttribute("assignmentDTO") AssignmentDTO assignmentDTO,
                                   RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Creating assignment - Asset ID: " + assignmentDTO.getAssetId());
            System.out.println("Creating assignment - Employee ID: " + assignmentDTO.getEmployeeId());
            System.out.println("Assigned Date: " + assignmentDTO.getAssignedDate());
            
            Assignment assignment = assignmentService.createAssignment(assignmentDTO);
            redirectAttributes.addFlashAttribute("success", "Asset assigned successfully!");
        } catch (Exception e) {
            System.err.println("Error creating assignment: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to assign: " + e.getMessage());
        }
        return "redirect:/assignments";
    }
    
    @GetMapping("/{id}/return")
    public String returnAsset(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            assignmentService.returnAsset(id, "GOOD", "Returned to inventory");
            redirectAttributes.addFlashAttribute("success", "Asset returned successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to return: " + e.getMessage());
        }
        return "redirect:/assignments";
    }
    
    @GetMapping("/active")
    public String activeAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getActiveAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("totalAssignments", assignments.size());
        model.addAttribute("filterType", "Active");
        model.addAttribute("activeCount", assignmentService.countActiveAssignments());
        model.addAttribute("overdueCount", assignmentService.countOverdueAssignments());
        return "assignments/list";
    }
    
    @GetMapping("/overdue")
    public String overdueAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getOverdueAssignments();
        model.addAttribute("assignments", assignments);
        model.addAttribute("totalAssignments", assignments.size());
        model.addAttribute("filterType", "Overdue");
        model.addAttribute("activeCount", assignmentService.countActiveAssignments());
        model.addAttribute("overdueCount", assignmentService.countOverdueAssignments());
        return "assignments/list";
    }
}