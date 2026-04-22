package com.airtel.inventory_system.service;

import com.airtel.inventory_system.dto.AssignmentDTO;
import com.airtel.inventory_system.entity.Assignment;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssignmentService {
    Assignment createAssignment(AssignmentDTO assignmentDTO);
    Assignment returnAsset(Long assignmentId, String returnCondition, String returnNotes);
    void cancelAssignment(Long id);
    Optional<Assignment> getAssignmentById(Long id);
    List<Assignment> getAssignmentsByAsset(Long assetId);
    List<Assignment> getAssignmentsByEmployee(Long employeeId);
    List<Assignment> getActiveAssignments();
    List<Assignment> getOverdueAssignments();
    List<Assignment> getAllAssignments();
    List<Assignment> getAssignmentsBetweenDates(LocalDate startDate, LocalDate endDate);
    Assignment getActiveAssignmentByAsset(Long assetId);
    List<Assignment> getActiveAssignmentsByEmployee(Long employeeId);
    Map<String, Long> getAssignmentStatistics();
    Long countActiveAssignments();
    Long countOverdueAssignments();
    void checkAndUpdateOverdueStatus();
    boolean isAssetAssigned(Long assetId);
}