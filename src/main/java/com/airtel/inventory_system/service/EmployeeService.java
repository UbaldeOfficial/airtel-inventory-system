package com.airtel.inventory_system.service;

import com.airtel.inventory_system.dto.EmployeeDTO;
import com.airtel.inventory_system.entity.Employee;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(EmployeeDTO employeeDTO);
    Employee updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    Optional<Employee> getEmployeeById(Long id);
    Optional<Employee> getEmployeeByEmployeeId(String employeeId);
    Optional<Employee> getEmployeeByEmail(String email);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByDepartment(String department);
    List<Employee> getActiveEmployees();
    List<Employee> searchEmployees(String name);
    List<Employee> getEmployeesWithActiveAssignments();
    Map<String, Long> getDepartmentStatistics();
    Long getActiveAssetsCount(Long employeeId);
    Long getTotalAssetsAssigned(Long employeeId);
    boolean hasActiveAssignments(Long employeeId);
}