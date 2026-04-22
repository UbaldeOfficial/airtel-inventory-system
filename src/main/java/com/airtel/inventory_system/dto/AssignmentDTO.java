package com.airtel.inventory_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    
    private Long id;
    private Long assetId;
    private String assetName;
    private String assetSerialNumber;
    private Long employeeId;
    private String employeeName;
    private String employeeDepartment;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedReturnDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate actualReturnDate;
    
    private String status;
    private String notes;
    private String assignedBy;
    private String purpose;
}