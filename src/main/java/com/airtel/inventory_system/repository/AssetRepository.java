package com.airtel.inventory_system.repository;

import com.airtel.inventory_system.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    Optional<Asset> findBySerialNumber(String serialNumber);
    
    Boolean existsBySerialNumber(String serialNumber);
    
    List<Asset> findByStatus(Asset.AssetStatus status);
    
    List<Asset> findByBrand(String brand);
    
    List<Asset> findByDeviceCondition(String deviceCondition);
    
    @Query("SELECT a FROM Asset a WHERE a.name LIKE %:keyword% OR a.serialNumber LIKE %:keyword% OR a.brand LIKE %:keyword%")
    List<Asset> searchAssets(@Param("keyword") String keyword);
    
    @Query("SELECT a FROM Asset a WHERE a.warrantyUntil BETWEEN :startDate AND :endDate")
    List<Asset> findAssetsWithWarrantyExpiring(@Param("startDate") LocalDate startDate, 
                                                @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(a) FROM Asset a WHERE a.status = :status")
    Long countByStatus(@Param("status") Asset.AssetStatus status);
    
    @Query("SELECT a.brand, COUNT(a) FROM Asset a GROUP BY a.brand")
    List<Object[]> countAssetsByBrand();
    
    @Query("SELECT a FROM Asset a WHERE a.nextMaintenanceDate <= :date")
    List<Asset> findAssetsDueForMaintenance(@Param("date") LocalDate date);
    
    @Query("SELECT a FROM Asset a WHERE a.qrCodePath IS NULL")
    List<Asset> findAssetsWithoutQRCode();
}