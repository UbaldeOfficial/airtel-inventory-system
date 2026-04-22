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
import com.airtel.inventory_system.service.PDFExportService;
import com.airtel.inventory_system.util.PDFGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PDFExportServiceImpl implements PDFExportService {
    
    @Autowired
    private PDFGenerator pdfGenerator;
    
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
    public void exportAssetsReport(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=assets_report.pdf");
            
            List<Asset> assets = assetRepository.findAll();
            pdfGenerator.generateAssetsReport(assets, response);
            
            auditLogService.logAction(com.airtel.inventory_system.entity.AuditLog.AuditAction.REPORT_GENERATED,
                "Assets PDF report exported", "Report", null, null);
        } catch (DocumentException e) {
            throw new IOException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void exportEmployeesReport(HttpServletResponse response) throws IOException {
        // Implementation for employee PDF export
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=employees_report.pdf");
        // Add implementation similar to assets report
    }
    
    @Override
    public void exportAssignmentsReport(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=assignments_report.pdf");
            
            List<Assignment> assignments = assignmentRepository.findAll();
            pdfGenerator.generateAssignmentsReport(assignments, response);
            
            auditLogService.logAction(com.airtel.inventory_system.entity.AuditLog.AuditAction.REPORT_GENERATED,
                "Assignments PDF report exported", "Report", null, null);
        } catch (DocumentException e) {
            throw new IOException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void exportAuditLogsReport(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=audit_logs_report.pdf");
            
            List<AuditLog> logs = auditLogRepository.findAll();
            pdfGenerator.generateAuditLogReport(logs, response);
            
            auditLogService.logAction(com.airtel.inventory_system.entity.AuditLog.AuditAction.REPORT_GENERATED,
                "Audit Logs PDF report exported", "Report", null, null);
        } catch (DocumentException e) {
            throw new IOException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void exportEmployeeAssetsReport(Long employeeId, HttpServletResponse response) throws IOException {
        // Implementation for employee-specific assets report
    }
    
    @Override
    public void exportAssetHistoryReport(Long assetId, HttpServletResponse response) throws IOException {
        // Implementation for asset history report
    }
    
    @Override
    public void exportCustomDateRangeReport(LocalDate startDate, LocalDate endDate, 
                                            HttpServletResponse response) throws IOException {
        // Implementation for custom date range report
    }
}