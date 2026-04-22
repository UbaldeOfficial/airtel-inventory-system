package com.airtel.inventory_system.repository;

import com.airtel.inventory_system.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByAssetId(Long assetId);
    
    List<AuditLog> findByEmployeeId(Long employeeId);
    
    List<AuditLog> findByUserId(Long userId);
    
    List<AuditLog> findByAction(AuditLog.AuditAction action);
    
    List<AuditLog> findByPerformedBy(String performedBy);
    
    @Query("SELECT a FROM AuditLog a ORDER BY a.timestamp DESC")
    List<AuditLog> findRecentLogs();
    
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end ORDER BY a.timestamp DESC")
    List<AuditLog> findLogsBetweenDates(@Param("start") LocalDateTime start, 
                                         @Param("end") LocalDateTime end);
    
    @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType AND a.entityId = :entityId ORDER BY a.timestamp DESC")
    List<AuditLog> findLogsByEntity(@Param("entityType") String entityType, 
                                     @Param("entityId") Long entityId);
    
    @Query(value = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT :limit", nativeQuery = true)
    List<AuditLog> findRecentLogsWithLimit(@Param("limit") int limit);
}