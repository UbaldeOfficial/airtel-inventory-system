package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.service.QRCodeService;
import com.airtel.inventory_system.util.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    
    @Value("${app.qrcode.storage.path:./src/main/resources/static/uploads/qrcodes/}")
    private String qrCodePath;
    
    @Override
    public String generateQRCode(Long assetId, String serialNumber, String assetName) {
        String qrData = String.format("AIRTEL|ASSET|%d|%s|%s", assetId, serialNumber, assetName);
        
        try {
            String fileName = "asset_" + assetId + "_" + serialNumber.replaceAll("[^a-zA-Z0-9]", "");
            return qrCodeGenerator.generateQRCode(qrData, fileName);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR Code: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String generateQRCodeWithLogo(Long assetId, String serialNumber, String assetName) {
        // For future enhancement - add Airtel logo to QR code
        return generateQRCode(assetId, serialNumber, assetName);
    }
    
    @Override
    public byte[] getQRCodeImage(String filePath) throws IOException {
        String fullPath = qrCodePath + filePath.replace("/uploads/qrcodes/", "");
        BufferedImage image = ImageIO.read(new File(fullPath));
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
    
    @Override
    public void deleteQRCode(String filePath) {
        try {
            String fullPath = qrCodePath + filePath.replace("/uploads/qrcodes/", "");
            Files.deleteIfExists(Paths.get(fullPath));
        } catch (IOException e) {
            System.err.println("Failed to delete QR Code: " + e.getMessage());
        }
    }
    
    @Override
    public String readQRCode(String filePath) {
        // Implementation for reading QR code using ZXing reader
        // This can be added later for scanning functionality
        return null;
    }
    
    @Override
    public boolean verifyQRCode(String qrCodeData, Long assetId) {
        return qrCodeData.contains("ASSET|" + assetId + "|");
    }
    
    public String generateQRCodeData(Asset asset) {
        return String.format("""
            AIRTEL INVENTORY SYSTEM
            ------------------------
            Asset ID: %d
            Name: %s
            Serial: %s
            Brand: %s
            ------------------------
            Scan to view asset details
            """, asset.getId(), asset.getName(), asset.getSerialNumber(), asset.getBrand());
    }
}