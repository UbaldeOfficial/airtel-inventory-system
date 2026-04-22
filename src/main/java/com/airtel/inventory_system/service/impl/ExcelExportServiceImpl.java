package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.entity.Assignment;
import com.airtel.inventory_system.entity.AuditLog;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.repository.AssetRepository;
import com.airtel.inventory_system.repository.AssignmentRepository;
import com.airtel.inventory_system.repository.AuditLogRepository;
import com.airtel.inventory_system.repository.EmployeeRepository;
import com.airtel.inventory_system.service.AuditLogService;
import com.airtel.inventory_system.service.ExcelExportService;
import com.airtel.inventory_system.util.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    
    @Autowired
    private ExcelGenerator excelGenerator;
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private AuditLogService auditLogService;
    
    @Override
    public void exportAssetsToExcel(HttpServletResponse response) throws IOException {
        List<Asset> assets = assetRepository.findAll();
        excelGenerator.generateAssetsExcel(assets, response);
        
        auditLogService.logAction(AuditLog.AuditAction.EXPORT,
            "Assets exported to Excel", "Export", null, "Total assets: " + assets.size());
    }
    
    @Override
    public void exportEmployeesToExcel(HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeRepository.findAll();
        excelGenerator.generateEmployeesExcel(employees, response);
        
        auditLogService.logAction(AuditLog.AuditAction.EXPORT,
            "Employees exported to Excel", "Export", null, "Total employees: " + employees.size());
    }
    
    @Override
    public void exportAssignmentsToExcel(HttpServletResponse response) throws IOException {
        List<Assignment> assignments = assignmentRepository.findAll();
        excelGenerator.generateAssignmentsExcel(assignments, response);
        
        auditLogService.logAction(AuditLog.AuditAction.EXPORT,
            "Assignments exported to Excel", "Export", null, "Total assignments: " + assignments.size());
    }
    
    @Override
    public void exportAuditLogsToExcel(HttpServletResponse response) throws IOException {
        // Implementation for audit logs Excel export
    }
    
    @Override
    public void exportDashboardData(HttpServletResponse response) throws IOException {
        // Implementation for dashboard data Excel export
    }
}