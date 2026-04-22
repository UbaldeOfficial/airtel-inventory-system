package com.airtel.inventory_system.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface PDFExportService {
    void exportAssetsReport(HttpServletResponse response) throws IOException;
    void exportEmployeesReport(HttpServletResponse response) throws IOException;
    void exportAssignmentsReport(HttpServletResponse response) throws IOException;
    void exportAuditLogsReport(HttpServletResponse response) throws IOException;
    void exportEmployeeAssetsReport(Long employeeId, HttpServletResponse response) throws IOException;
    void exportAssetHistoryReport(Long assetId, HttpServletResponse response) throws IOException;
    void exportCustomDateRangeReport(LocalDate startDate, LocalDate endDate, 
                                    HttpServletResponse response) throws IOException;
}