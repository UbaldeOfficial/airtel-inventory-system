package com.airtel.inventory_system.repository;

import com.airtel.inventory_system.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByAssetId(Long assetId);
    
    List<Assignment> findByEmployeeId(Long employeeId);
    
    List<Assignment> findByStatus(Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.asset.id = :assetId AND a.status = 'ACTIVE'")
    Optional<Assignment> findActiveAssignmentByAssetId(@Param("assetId") Long assetId);
    
    @Query("SELECT a FROM Assignment a WHERE a.employee.id = :employeeId AND a.status = 'ACTIVE'")
    List<Assignment> findActiveAssignmentsByEmployeeId(@Param("employeeId") Long employeeId);
    
    @Query("SELECT a FROM Assignment a WHERE a.expectedReturnDate < :date AND a.status = 'ACTIVE'")
    List<Assignment> findOverdueAssignments(@Param("date") LocalDate date);
    
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.status = :status")
    Long countByStatus(@Param("status") Assignment.AssignmentStatus status);
    
    @Query("SELECT a FROM Assignment a WHERE a.assignedDate BETWEEN :startDate AND :endDate")
    List<Assignment> findAssignmentsBetweenDates(@Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate);
}