package com.airtel.inventory_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    
    // Asset Statistics
    private Long totalAssets;
    private Long availableAssets;
    private Long assignedAssets;
    private Long damagedAssets;
    private Long underMaintenanceAssets;
    
    // Employee Statistics
    private Long totalEmployees;
    private Long activeEmployees;
    private Map<String, Long> employeesByDepartment;
    
    // Assignment Statistics
    private Long activeAssignments;
    private Long overdueAssignments;
    private Long totalAssignmentsThisMonth;
    
    // Recent Activities
    private Object recentAuditLogs;
    private Object recentAssignments;
    
    // Chart Data
    private Map<String, Long> assetsByStatus;
    private Map<String, Long> assetsByBrand;
    private Map<String, Long> assignmentsByDepartment;
    
    // QR Code Stats
    private Long assetsWithQRCode;
    private Long assetsWithoutQRCode;
}