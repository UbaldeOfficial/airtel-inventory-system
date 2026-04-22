package com.airtel.inventory_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetDTO {
    
    private Long id;
    
    @NotBlank(message = "Asset name is required")
    private String name;
    
    @NotBlank(message = "Brand is required")
    private String brand;
    
    @NotBlank(message = "Serial number is required")
    private String serialNumber;
    
    private String deviceCondition;
    
    @NotNull(message = "Purchase date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;
    
    private String description;
    private String model;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyUntil;
    
    private String status;
    private String qrCodePath;
    private String location;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastMaintenanceDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate nextMaintenanceDate;
}