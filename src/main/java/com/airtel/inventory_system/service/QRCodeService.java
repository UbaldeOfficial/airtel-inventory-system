package com.airtel.inventory_system.service;

import java.io.IOException;

public interface QRCodeService {
    String generateQRCode(Long assetId, String serialNumber, String assetName);
    String generateQRCodeWithLogo(Long assetId, String serialNumber, String assetName);
    byte[] getQRCodeImage(String filePath) throws IOException;
    void deleteQRCode(String filePath);
    String readQRCode(String filePath);
    boolean verifyQRCode(String qrCodeData, Long assetId);
}