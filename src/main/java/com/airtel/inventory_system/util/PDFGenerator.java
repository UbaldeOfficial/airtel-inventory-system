package com.airtel.inventory_system.util;

import com.airtel.inventory_system.entity.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFGenerator {
    
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    
    public void generateAssetsReport(List<Asset> assets, HttpServletResponse response) 
            throws DocumentException, IOException {
        
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        
        // Add Title
        Paragraph title = new Paragraph("AIRTEL INVENTORY MANAGEMENT SYSTEM", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        Paragraph subtitle = new Paragraph("Assets Report", HEADER_FONT);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);
        
        document.add(new Paragraph(" "));
        
        // Add Generation Date
        Paragraph date = new Paragraph("Generated: " + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), SMALL_FONT);
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);
        
        document.add(new Paragraph(" "));
        
        // Create Table
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        // Set Column Widths
        float[] columnWidths = {1f, 2f, 1.5f, 2f, 1.5f, 1.5f, 1.5f, 1.5f};
        table.setWidths(columnWidths);
        
        // Add Headers
        addTableHeader(table, new String[]{
            "ID", "Asset Name", "Brand", "Serial Number", "Condition", 
            "Status", "Purchase Date", "Location"
        });
        
        // Add Data
        for (Asset asset : assets) {
            table.addCell(new Phrase(String.valueOf(asset.getId()), NORMAL_FONT));
            table.addCell(new Phrase(asset.getName(), NORMAL_FONT));
            table.addCell(new Phrase(asset.getBrand(), NORMAL_FONT));
            table.addCell(new Phrase(asset.getSerialNumber(), NORMAL_FONT));
            table.addCell(new Phrase(asset.getDeviceCondition() != null ? 
                asset.getDeviceCondition() : "-", NORMAL_FONT));
            table.addCell(new Phrase(asset.getStatus().toString(), NORMAL_FONT));
            table.addCell(new Phrase(asset.getPurchaseDate().toString(), NORMAL_FONT));
            table.addCell(new Phrase(asset.getLocation() != null ? 
                asset.getLocation() : "-", NORMAL_FONT));
        }
        
        document.add(table);
        
        // Add Summary
        document.add(new Paragraph(" "));
        Paragraph summary = new Paragraph("Total Assets: " + assets.size(), HEADER_FONT);
        document.add(summary);
        
        // Add Footer
        document.add(new Paragraph(" "));
        Paragraph footer = new Paragraph("AIRTEL - Confidential", SMALL_FONT);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
        
        document.close();
    }
    
    private void addTableHeader(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorderWidth(1);
            cell.setPhrase(new Phrase(header, HEADER_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
    
    public void generateAssignmentsReport(List<Assignment> assignments, HttpServletResponse response) 
            throws DocumentException, IOException {
        
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        
        // Title
        Paragraph title = new Paragraph("AIRTEL INVENTORY MANAGEMENT SYSTEM", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        Paragraph subtitle = new Paragraph("Asset Assignments Report", HEADER_FONT);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);
        
        document.add(new Paragraph(" "));
        
        // Table
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        
        addTableHeader(table, new String[]{
            "ID", "Asset", "Serial No.", "Employee", "Department", 
            "Assigned Date", "Expected Return", "Status", "Assigned By"
        });
        
        for (Assignment assignment : assignments) {
            table.addCell(new Phrase(String.valueOf(assignment.getId()), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getAsset().getName(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getAsset().getSerialNumber(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getEmployee().getFullName(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getEmployee().getDepartment(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getAssignedDate().toString(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getExpectedReturnDate() != null ? 
                assignment.getExpectedReturnDate().toString() : "-", NORMAL_FONT));
            table.addCell(new Phrase(assignment.getStatus().toString(), NORMAL_FONT));
            table.addCell(new Phrase(assignment.getAssignedBy() != null ? 
                assignment.getAssignedBy() : "-", NORMAL_FONT));
        }
        
        document.add(table);
        document.close();
    }
    
    public void generateAuditLogReport(List<AuditLog> logs, HttpServletResponse response) 
            throws DocumentException, IOException {
        
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        
        Paragraph title = new Paragraph("AIRTEL - AUDIT TRAIL REPORT", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        
        addTableHeader(table, new String[]{
            "Timestamp", "Action", "Performed By", "Role", "Entity Type", "Description"
        });
        
        for (AuditLog log : logs) {
            table.addCell(new Phrase(log.getTimestamp().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), NORMAL_FONT));
            table.addCell(new Phrase(log.getAction().toString(), NORMAL_FONT));
            table.addCell(new Phrase(log.getPerformedBy(), NORMAL_FONT));
            table.addCell(new Phrase(log.getUserRole() != null ? log.getUserRole() : "-", NORMAL_FONT));
            table.addCell(new Phrase(log.getEntityType() != null ? log.getEntityType() : "-", NORMAL_FONT));
            table.addCell(new Phrase(log.getDescription(), NORMAL_FONT));
        }
        
        document.add(table);
        document.close();
    }
}