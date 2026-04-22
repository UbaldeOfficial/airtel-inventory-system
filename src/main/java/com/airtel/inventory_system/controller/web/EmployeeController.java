package com.airtel.inventory_system.controller.web;

import com.airtel.inventory_system.dto.EmployeeDTO;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    public String listEmployees(Model model, @RequestParam(required = false) String search) {
        List<Employee> employees;
        if (search != null && !search.isEmpty()) {
            employees = employeeService.searchEmployees(search);
            model.addAttribute("search", search);
        } else {
            employees = employeeService.getAllEmployees();
        }
        
        model.addAttribute("employees", employees);
        model.addAttribute("totalEmployees", employees.size());
        model.addAttribute("activeCount", employeeService.getActiveEmployees().size());
        
        return "employees/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "employees/form";
    }
    
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.createEmployee(employeeDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Employee created successfully: " + employee.getFullName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to create employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
            
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setEmployeeId(employee.getEmployeeId());
            employeeDTO.setFullName(employee.getFullName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setDepartment(employee.getDepartment());
            employeeDTO.setDesignation(employee.getDesignation());
            employeeDTO.setPhoneNumber(employee.getPhoneNumber());
            employeeDTO.setHireDate(employee.getHireDate());
            employeeDTO.setAddress(employee.getAddress());
            employeeDTO.setActive(employee.isActive());
            
            model.addAttribute("employeeDTO", employeeDTO);
            return "employees/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Employee not found");
            return "redirect:/employees";
        }
    }
    
    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.updateEmployee(id, employeeDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Employee updated successfully: " + employee.getFullName());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to update employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }
    
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("success", "Employee deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete employee: " + e.getMessage());
        }
        return "redirect:/employees";
    }
    
    @GetMapping("/department/{department}")
    public String filterByDepartment(@PathVariable String department, Model model) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        model.addAttribute("employees", employees);
        model.addAttribute("filterDepartment", department);
        model.addAttribute("totalEmployees", employees.size());
        return "employees/list";
    }
}