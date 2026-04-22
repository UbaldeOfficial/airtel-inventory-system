package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.dto.EmployeeDTO;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.exception.DuplicateResourceException;
import com.airtel.inventory_system.exception.ResourceNotFoundException;
import com.airtel.inventory_system.repository.EmployeeRepository;
import com.airtel.inventory_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmployeeId(employeeDTO.getEmployeeId())) {
            throw new DuplicateResourceException("Employee ID already exists: " + employeeDTO.getEmployeeId());
        }
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + employeeDTO.getEmail());
        }
        
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setFullName(employeeDTO.getFullName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setAddress(employeeDTO.getAddress());
        employee.setActive(employeeDTO.getActive() != null ? employeeDTO.getActive() : true);
        
        return employeeRepository.save(employee);
    }
    
    @Override
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        
        employee.setFullName(employeeDTO.getFullName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setAddress(employeeDTO.getAddress());
        employee.setActive(employeeDTO.getActive());
        
        return employeeRepository.save(employee);
    }
    
    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setActive(false);
        employeeRepository.save(employee);
    }
    
    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    @Override
    public Optional<Employee> getEmployeeByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
    
    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    @Override
    public List<Employee> getActiveEmployees() {
        return employeeRepository.findByActive(true);
    }
    
    @Override
    public List<Employee> searchEmployees(String name) {
        return employeeRepository.searchByName(name);
    }
    
    @Override
    public List<Employee> getEmployeesWithActiveAssignments() {
        return employeeRepository.findEmployeesWithActiveAssignments();
    }
    
    @Override
    public Map<String, Long> getDepartmentStatistics() {
        Map<String, Long> stats = new HashMap<>();
        List<Object[]> results = employeeRepository.countEmployeesByDepartment();
        for (Object[] result : results) {
            stats.put((String) result[0], (Long) result[1]);
        }
        return stats;
    }
    
    @Override
    public Long getActiveAssetsCount(Long employeeId) {
        return 0L; // To be implemented
    }
    
    @Override
    public Long getTotalAssetsAssigned(Long employeeId) {
        return 0L; // To be implemented
    }
    
    @Override
    public boolean hasActiveAssignments(Long employeeId) {
        return !employeeRepository.findEmployeesWithActiveAssignments()
            .stream()
            .filter(e -> e.getId().equals(employeeId))
            .toList()
            .isEmpty();
    }
}