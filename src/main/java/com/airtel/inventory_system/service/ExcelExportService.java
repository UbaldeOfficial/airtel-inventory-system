package com.airtel.inventory_system.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelExportService {
    void exportAssetsToExcel(HttpServletResponse response) throws IOException;
    void exportEmployeesToExcel(HttpServletResponse response) throws IOException;
    void exportAssignmentsToExcel(HttpServletResponse response) throws IOException;
    void exportAuditLogsToExcel(HttpServletResponse response) throws IOException;
    void exportDashboardData(HttpServletResponse response) throws IOException;
}