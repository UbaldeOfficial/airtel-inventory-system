package com.airtel.inventory_system.service;

import com.airtel.inventory_system.entity.AuditLog;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {
    AuditLog logAction(AuditLog.AuditAction action, String description, 
                       String entityType, Long entityId, String details);
    AuditLog logAssetAction(AuditLog.AuditAction action, Long assetId, String description);
    AuditLog logEmployeeAction(AuditLog.AuditAction action, Long employeeId, String description);
    AuditLog logAssignmentAction(AuditLog.AuditAction action, Long assignmentId, String description);
    AuditLog logLogin(String username);
    AuditLog logLogout(String username);
    List<AuditLog> getRecentLogs(int limit);
    List<AuditLog> getLogsByAsset(Long assetId);
    List<AuditLog> getLogsByEmployee(Long employeeId);
    List<AuditLog> getLogsByUser(Long userId);
    List<AuditLog> getLogsByAction(AuditLog.AuditAction action);
    List<AuditLog> getLogsBetweenDates(LocalDateTime start, LocalDateTime end);
    List<AuditLog> getAllLogs();
    void deleteOldLogs(int days);
}