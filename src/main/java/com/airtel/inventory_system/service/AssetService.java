package com.airtel.inventory_system.service;

import com.airtel.inventory_system.dto.AssetDTO;
import com.airtel.inventory_system.entity.Asset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssetService {
    Asset createAsset(AssetDTO assetDTO);
    Asset updateAsset(Long id, AssetDTO assetDTO);
    void deleteAsset(Long id);
    void restoreAsset(Long id);
    Optional<Asset> getAssetById(Long id);
    Optional<Asset> getAssetBySerialNumber(String serialNumber);
    List<Asset> getAllAssets();
    List<Asset> getAssetsByStatus(Asset.AssetStatus status);
    List<Asset> searchAssets(String keyword);
    List<Asset> getAssetsDueForMaintenance();
    List<Asset> getAssetsWithWarrantyExpiring(LocalDate startDate, LocalDate endDate);
    String generateQRCodeForAsset(Long assetId);
    void regenerateAllQRCodes();
    Map<String, Long> getAssetStatistics();
    Long countByStatus(Asset.AssetStatus status);
    boolean isAssetAvailable(Long assetId);
    void updateAssetStatus(Long assetId, Asset.AssetStatus status);
}