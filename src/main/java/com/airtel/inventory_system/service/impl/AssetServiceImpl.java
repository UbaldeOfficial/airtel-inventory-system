package com.airtel.inventory_system.service.impl;

import com.airtel.inventory_system.dto.AssetDTO;
import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.exception.ResourceNotFoundException;
import com.airtel.inventory_system.repository.AssetRepository;
import com.airtel.inventory_system.service.AssetService;
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
public class AssetServiceImpl implements AssetService {
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Override
    public Asset createAsset(AssetDTO assetDTO) {
        Asset asset = new Asset();
        asset.setName(assetDTO.getName());
        asset.setBrand(assetDTO.getBrand());
        asset.setSerialNumber(assetDTO.getSerialNumber());
        asset.setDeviceCondition(assetDTO.getDeviceCondition());
        asset.setPurchaseDate(assetDTO.getPurchaseDate());
        asset.setDescription(assetDTO.getDescription());
        asset.setModel(assetDTO.getModel());
        asset.setWarrantyUntil(assetDTO.getWarrantyUntil());
        asset.setLocation(assetDTO.getLocation());
        asset.setLastMaintenanceDate(assetDTO.getLastMaintenanceDate());
        asset.setNextMaintenanceDate(assetDTO.getNextMaintenanceDate());
        
        if (assetDTO.getStatus() != null) {
            asset.setStatus(Asset.AssetStatus.valueOf(assetDTO.getStatus()));
        }
        
        return assetRepository.save(asset);
    }
    
    @Override
    public Asset updateAsset(Long id, AssetDTO assetDTO) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
        
        asset.setName(assetDTO.getName());
        asset.setBrand(assetDTO.getBrand());
        asset.setSerialNumber(assetDTO.getSerialNumber());
        asset.setDeviceCondition(assetDTO.getDeviceCondition());
        asset.setPurchaseDate(assetDTO.getPurchaseDate());
        asset.setDescription(assetDTO.getDescription());
        asset.setModel(assetDTO.getModel());
        asset.setWarrantyUntil(assetDTO.getWarrantyUntil());
        asset.setLocation(assetDTO.getLocation());
        asset.setLastMaintenanceDate(assetDTO.getLastMaintenanceDate());
        asset.setNextMaintenanceDate(assetDTO.getNextMaintenanceDate());
        
        if (assetDTO.getStatus() != null) {
            asset.setStatus(Asset.AssetStatus.valueOf(assetDTO.getStatus()));
        }
        
        return assetRepository.save(asset);
    }
    
    @Override
    public void deleteAsset(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
        assetRepository.delete(asset);
    }
    
    @Override
    public void restoreAsset(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
        asset.setDeleted(false);
        assetRepository.save(asset);
    }
    
    @Override
    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }
    
    @Override
    public Optional<Asset> getAssetBySerialNumber(String serialNumber) {
        return assetRepository.findBySerialNumber(serialNumber);
    }
    
    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    @Override
    public List<Asset> getAssetsByStatus(Asset.AssetStatus status) {
        return assetRepository.findByStatus(status);
    }
    
    @Override
    public List<Asset> searchAssets(String keyword) {
        return assetRepository.searchAssets(keyword);
    }
    
    @Override
    public List<Asset> getAssetsDueForMaintenance() {
        return assetRepository.findAssetsDueForMaintenance(LocalDate.now());
    }
    
    @Override
    public List<Asset> getAssetsWithWarrantyExpiring(LocalDate startDate, LocalDate endDate) {
        return assetRepository.findAssetsWithWarrantyExpiring(startDate, endDate);
    }
    
    @Override
    public String generateQRCodeForAsset(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", assetId));
        
        String qrCodePath = "/uploads/qrcodes/asset_" + assetId + ".png";
        asset.setQrCodePath(qrCodePath);
        assetRepository.save(asset);
        
        return qrCodePath;
    }
    
    @Override
    public void regenerateAllQRCodes() {
        // Implementation can be added later
    }
    
    @Override
    public Map<String, Long> getAssetStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", assetRepository.count());
        stats.put("available", assetRepository.countByStatus(Asset.AssetStatus.AVAILABLE));
        stats.put("assigned", assetRepository.countByStatus(Asset.AssetStatus.ASSIGNED));
        stats.put("damaged", assetRepository.countByStatus(Asset.AssetStatus.DAMAGED));
        stats.put("maintenance", assetRepository.countByStatus(Asset.AssetStatus.UNDER_MAINTENANCE));
        return stats;
    }
    
    @Override
    public Long countByStatus(Asset.AssetStatus status) {
        return assetRepository.countByStatus(status);
    }
    
    @Override
    public boolean isAssetAvailable(Long assetId) {
        return assetRepository.findById(assetId)
            .map(asset -> asset.getStatus() == Asset.AssetStatus.AVAILABLE)
            .orElse(false);
    }
    
    @Override
    public void updateAssetStatus(Long assetId, Asset.AssetStatus status) {
        Asset asset = assetRepository.findById(assetId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", assetId));
        asset.setStatus(status);
        assetRepository.save(asset);
    }
}