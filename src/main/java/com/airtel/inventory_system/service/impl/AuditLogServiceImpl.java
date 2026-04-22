package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.entity.AuditLog;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.entity.User;
import com.airtel.inventory_system.repository.AssetRepository;
import com.airtel.inventory_system.repository.AuditLogRepository;
import com.airtel.inventory_system.repository.EmployeeRepository;
import com.airtel.inventory_system.repository.UserRepository;
import com.airtel.inventory_system.service.AuditLogService;
import com.airtel.inventory_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired(required = false)
    private HttpServletRequest request;
    
    @Override
    public AuditLog logAction(AuditLog.AuditAction action, String description, 
                              String entityType, Long entityId, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setDescription(description);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            log.setPerformedBy(currentUser.getFullName());
            log.setUser(currentUser);
            log.setUserRole(currentUser.getRoles().toString());
        } else {
            log.setPerformedBy("System");
            log.setUserRole("SYSTEM");
        }
        
        if (request != null) {
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            log.setIpAddress(ipAddress);
        }
        
        return auditLogRepository.save(log);
    }
    
    @Override
    public AuditLog logAssetAction(AuditLog.AuditAction action, Long assetId, String description) {
        AuditLog log = logAction(action, description, "Asset", assetId, null);
        
        if (assetId != null) {
            assetRepository.findById(assetId).ifPresent(log::setAsset);
        }
        
        return auditLogRepository.save(log);
    }
    
    @Override
    public AuditLog logEmployeeAction(AuditLog.AuditAction action, Long employeeId, String description) {
        AuditLog log = logAction(action, description, "Employee", employeeId, null);
        
        if (employeeId != null) {
            employeeRepository.findById(employeeId).ifPresent(log::setEmployee);
        }
        
        return auditLogRepository.save(log);
    }
    
    @Override
    public AuditLog logAssignmentAction(AuditLog.AuditAction action, Long assignmentId, String description) {
        return logAction(action, description, "Assignment", assignmentId, null);
    }
    
    @Override
    public AuditLog logLogin(String username) {
        AuditLog log = new AuditLog();
        log.setAction(AuditLog.AuditAction.LOGIN);
        log.setDescription("User logged in: " + username);
        log.setPerformedBy(username);
        log.setTimestamp(LocalDateTime.now());
        
        userRepository.findByUsername(username).ifPresent(user -> {
            log.setUser(user);
            log.setUserRole(user.getRoles().toString());
        });
        
        if (request != null) {
            log.setIpAddress(request.getRemoteAddr());
        }
        
        return auditLogRepository.save(log);
    }
    
    @Override
    public AuditLog logLogout(String username) {
        AuditLog log = new AuditLog();
        log.setAction(AuditLog.AuditAction.LOGOUT);
        log.setDescription("User logged out: " + username);
        log.setPerformedBy(username);
        log.setTimestamp(LocalDateTime.now());
        
        if (request != null) {
            log.setIpAddress(request.getRemoteAddr());
        }
        
        return auditLogRepository.save(log);
    }
    
    @Override
    public List<AuditLog> getRecentLogs(int limit) {
        return auditLogRepository.findRecentLogsWithLimit(limit);
    }
    
    @Override
    public List<AuditLog> getLogsByAsset(Long assetId) {
        return auditLogRepository.findByAssetId(assetId);
    }
    
    @Override
    public List<AuditLog> getLogsByEmployee(Long employeeId) {
        return auditLogRepository.findByEmployeeId(employeeId);
    }
    
    @Override
    public List<AuditLog> getLogsByUser(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }
    
    @Override
    public List<AuditLog> getLogsByAction(AuditLog.AuditAction action) {
        return auditLogRepository.findByAction(action);
    }
    
    @Override
    public List<AuditLog> getLogsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findLogsBetweenDates(start, end);
    }
    
    @Override
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
    
    @Override
    public void deleteOldLogs(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        List<AuditLog> oldLogs = auditLogRepository.findLogsBetweenDates(
            LocalDateTime.of(2000, 1, 1, 0, 0), cutoffDate);
        auditLogRepository.deleteAll(oldLogs);
    }
}