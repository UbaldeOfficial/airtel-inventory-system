package com.airtel.inventory_system.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class QRCodeGenerator {
    
    @Value("${app.qrcode.storage.path:./src/main/resources/static/uploads/qrcodes/}")
    private String qrCodePath;
    
    private static final int WIDTH = 350;
    private static final int HEIGHT = 350;
    
    public String generateQRCode(String data, String fileName) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        
        // Create directory if not exists
        Path path = Paths.get(qrCodePath);
        if (!path.toFile().exists()) {
            path.toFile().mkdirs();
        }
        
        // Generate unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fullFileName = fileName + "_" + timestamp + ".png";
        Path filePath = Paths.get(qrCodePath + fullFileName);
        
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
        
        return "/uploads/qrcodes/" + fullFileName;
    }
    
    public String generateAssetQRData(Long assetId, String serialNumber, String assetName) {
        return String.format("AIRTEL|ASSET|%d|%s|%s", assetId, serialNumber, assetName);
    }
}