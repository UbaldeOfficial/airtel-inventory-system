package com.airtel.inventory_system.util;

import com.airtel.inventory_system.entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ExcelGenerator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public void generateAssetsExcel(List<Asset> assets, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Assets");
        
        // Create Header Row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Brand", "Serial Number", "Condition", 
                           "Status", "Purchase Date", "Location", "QR Code"};
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Create Data Rows
        int rowNum = 1;
        CellStyle dataStyle = createDataStyle(workbook);
        
        for (Asset asset : assets) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(asset.getId());
            row.createCell(1).setCellValue(asset.getName());
            row.createCell(2).setCellValue(asset.getBrand());
            row.createCell(3).setCellValue(asset.getSerialNumber());
            row.createCell(4).setCellValue(asset.getDeviceCondition() != null ? 
                asset.getDeviceCondition() : "");
            row.createCell(5).setCellValue(asset.getStatus().toString());
            row.createCell(6).setCellValue(asset.getPurchaseDate().format(DATE_FORMATTER));
            row.createCell(7).setCellValue(asset.getLocation() != null ? 
                asset.getLocation() : "");
            row.createCell(8).setCellValue(asset.getQrCodePath() != null ? 
                "Generated" : "Not Generated");
            
            for (int i = 0; i < headers.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Write to response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=assets_report.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    public void generateEmployeesExcel(List<Employee> employees, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Employee ID", "Full Name", "Email", "Department", 
                           "Designation", "Phone", "Hire Date", "Status"};
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        int rowNum = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getEmployeeId());
            row.createCell(2).setCellValue(employee.getFullName());
            row.createCell(3).setCellValue(employee.getEmail());
            row.createCell(4).setCellValue(employee.getDepartment());
            row.createCell(5).setCellValue(employee.getDesignation() != null ? 
                employee.getDesignation() : "");
            row.createCell(6).setCellValue(employee.getPhoneNumber() != null ? 
                employee.getPhoneNumber() : "");
            row.createCell(7).setCellValue(employee.getHireDate() != null ? 
                employee.getHireDate().format(DATE_FORMATTER) : "");
            row.createCell(8).setCellValue(employee.isActive() ? "Active" : "Inactive");
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees_report.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    public void generateAssignmentsExcel(List<Assignment> assignments, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Assignments");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Asset", "Serial No.", "Employee", "Department", 
                           "Assigned Date", "Expected Return", "Actual Return", "Status"};
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        int rowNum = 1;
        for (Assignment assignment : assignments) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(assignment.getId());
            row.createCell(1).setCellValue(assignment.getAsset().getName());
            row.createCell(2).setCellValue(assignment.getAsset().getSerialNumber());
            row.createCell(3).setCellValue(assignment.getEmployee().getFullName());
            row.createCell(4).setCellValue(assignment.getEmployee().getDepartment());
            row.createCell(5).setCellValue(assignment.getAssignedDate().format(DATE_FORMATTER));
            row.createCell(6).setCellValue(assignment.getExpectedReturnDate() != null ? 
                assignment.getExpectedReturnDate().format(DATE_FORMATTER) : "");
            row.createCell(7).setCellValue(assignment.getActualReturnDate() != null ? 
                assignment.getActualReturnDate().format(DATE_FORMATTER) : "");
            row.createCell(8).setCellValue(assignment.getStatus().toString());
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=assignments_report.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}