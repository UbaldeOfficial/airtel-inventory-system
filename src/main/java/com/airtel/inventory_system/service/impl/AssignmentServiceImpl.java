package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.dto.AssignmentDTO;
import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.entity.Assignment;
import com.airtel.inventory_system.entity.Employee;
import com.airtel.inventory_system.exception.BusinessRuleViolationException;
import com.airtel.inventory_system.exception.ResourceNotFoundException;
import com.airtel.inventory_system.repository.AssetRepository;
import com.airtel.inventory_system.repository.AssignmentRepository;
import com.airtel.inventory_system.repository.EmployeeRepository;
import com.airtel.inventory_system.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public Assignment createAssignment(AssignmentDTO assignmentDTO) {
        Asset asset = assetRepository.findById(assignmentDTO.getAssetId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", assignmentDTO.getAssetId()));
        
        if (asset.getStatus() != Asset.AssetStatus.AVAILABLE) {
            throw new BusinessRuleViolationException("Asset is not available for assignment");
        }
        
        Employee employee = employeeRepository.findById(assignmentDTO.getEmployeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", assignmentDTO.getEmployeeId()));
        
        Assignment assignment = new Assignment();
        assignment.setAsset(asset);
        assignment.setEmployee(employee);
        assignment.setAssignedDate(assignmentDTO.getAssignedDate());
        assignment.setExpectedReturnDate(assignmentDTO.getExpectedReturnDate());
        assignment.setNotes(assignmentDTO.getNotes());
        assignment.setPurpose(assignmentDTO.getPurpose());
        assignment.setStatus(Assignment.AssignmentStatus.ACTIVE);
        assignment.setAssignedBy("System");
        
        Assignment savedAssignment = assignmentRepository.save(assignment);
        
        asset.setStatus(Asset.AssetStatus.ASSIGNED);
        assetRepository.save(asset);
        
        return savedAssignment;
    }
    
    @Override
    public Assignment returnAsset(Long assignmentId, String returnCondition, String returnNotes) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
        
        assignment.setActualReturnDate(LocalDate.now());
        assignment.setReturnCondition(returnCondition);
        assignment.setReturnNotes(returnNotes);
        assignment.setStatus(Assignment.AssignmentStatus.RETURNED);
        
        Assignment returnedAssignment = assignmentRepository.save(assignment);
        
        Asset asset = assignment.getAsset();
        asset.setStatus(Asset.AssetStatus.AVAILABLE);
        asset.setDeviceCondition(returnCondition);
        assetRepository.save(asset);
        
        return returnedAssignment;
    }
    
    @Override
    public void cancelAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", id));
        
        assignment.setStatus(Assignment.AssignmentStatus.CANCELLED);
        assignmentRepository.save(assignment);
        
        Asset asset = assignment.getAsset();
        asset.setStatus(Asset.AssetStatus.AVAILABLE);
        assetRepository.save(asset);
    }
    
    @Override
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }
    
    @Override
    public List<Assignment> getAssignmentsByAsset(Long assetId) {
        return assignmentRepository.findByAssetId(assetId);
    }
    
    @Override
    public List<Assignment> getAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId);
    }
    
    @Override
    public List<Assignment> getActiveAssignments() {
        return assignmentRepository.findByStatus(Assignment.AssignmentStatus.ACTIVE);
    }
    
    @Override
    public List<Assignment> getOverdueAssignments() {
        return assignmentRepository.findOverdueAssignments(LocalDate.now());
    }
    
    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    
    @Override
    public List<Assignment> getAssignmentsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return assignmentRepository.findAssignmentsBetweenDates(startDate, endDate);
    }
    
    @Override
    public Assignment getActiveAssignmentByAsset(Long assetId) {
        return assignmentRepository.findActiveAssignmentByAssetId(assetId).orElse(null);
    }
    
    @Override
    public List<Assignment> getActiveAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findActiveAssignmentsByEmployeeId(employeeId);
    }
    
    @Override
    public Map<String, Long> getAssignmentStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", assignmentRepository.count());
        stats.put("active", assignmentRepository.countByStatus(Assignment.AssignmentStatus.ACTIVE));
        stats.put("returned", assignmentRepository.countByStatus(Assignment.AssignmentStatus.RETURNED));
        stats.put("overdue", (long) getOverdueAssignments().size());
        return stats;
    }
    
    @Override
    public Long countActiveAssignments() {
        return assignmentRepository.countByStatus(Assignment.AssignmentStatus.ACTIVE);
    }
    
    @Override
    public Long countOverdueAssignments() {
        return (long) getOverdueAssignments().size();
    }
    
    @Override
    public void checkAndUpdateOverdueStatus() {
        List<Assignment> overdueAssignments = getOverdueAssignments();
        for (Assignment assignment : overdueAssignments) {
            assignment.setStatus(Assignment.AssignmentStatus.OVERDUE);
            assignmentRepository.save(assignment);
        }
    }
    
    @Override
    public boolean isAssetAssigned(Long assetId) {
        return assignmentRepository.findActiveAssignmentByAssetId(assetId).isPresent();
    }
}