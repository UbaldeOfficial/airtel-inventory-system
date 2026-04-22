package com.airtel.inventory_system.repository;

import com.airtel.inventory_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmployeeId(String employeeId);
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByDepartment(String department);
    
    List<Employee> findByActive(Boolean active);
    
    Boolean existsByEmployeeId(String employeeId);
    
    Boolean existsByEmail(String email);
    
    @Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> searchByName(@Param("name") String name);
    
    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countEmployeesByDepartment();
    
    @Query("SELECT e FROM Employee e WHERE e.id IN (SELECT a.employee.id FROM Assignment a WHERE a.status = 'ACTIVE')")
    List<Employee> findEmployeesWithActiveAssignments();
}