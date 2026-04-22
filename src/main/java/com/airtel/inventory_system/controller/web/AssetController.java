package com.airtel.inventory_system.controller.web;

import com.airtel.inventory_system.dto.AssetDTO;
import com.airtel.inventory_system.entity.Asset;
import com.airtel.inventory_system.service.AssetService;
import com.airtel.inventory_system.service.AssignmentService;
import com.airtel.inventory_system.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/assets")
@PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private AuditLogService auditLogService;
    
    @GetMapping
    public String listAssets(Model model, @RequestParam(required = false) String search) {
        List<Asset> assets;
        if (search != null && !search.isEmpty()) {
            assets = assetService.searchAssets(search);
            model.addAttribute("search", search);
        } else {
            assets = assetService.getAllAssets();
        }
        
        model.addAttribute("assets", assets);
        model.addAttribute("totalAssets", assets.size());
        model.addAttribute("availableCount", assetService.countByStatus(Asset.AssetStatus.AVAILABLE));
        model.addAttribute("assignedCount", assetService.countByStatus(Asset.AssetStatus.ASSIGNED));
        
        return "assets/list";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setStatus("AVAILABLE");
        model.addAttribute("assetDTO", assetDTO);
        return "assets/form";
    }
    
    @PostMapping("/create")
    public String createAsset(@Valid @ModelAttribute("assetDTO") AssetDTO assetDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("assetDTO", assetDTO);
            model.addAttribute("error", "Please fix the errors in the form");
            return "assets/form";
        }
        
        try {
            System.out.println("Creating asset: " + assetDTO.getName());
            System.out.println("Purchase Date: " + assetDTO.getPurchaseDate());
            
            Asset asset = assetService.createAsset(assetDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Asset created successfully: " + asset.getName());
            System.out.println("Asset created with ID: " + asset.getId());
        } catch (Exception e) {
            System.err.println("Error creating asset: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", 
                "Failed to create asset: " + e.getMessage());
        }
        
        return "redirect:/assets";
    }
    
    @GetMapping("/{id}")
    public String viewAsset(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Asset asset = assetService.getAssetById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));
            
            model.addAttribute("asset", asset);
            model.addAttribute("assignmentHistory", assignmentService.getAssignmentsByAsset(id));
            model.addAttribute("auditLogs", auditLogService.getLogsByAsset(id));
            
            return "assets/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Asset not found");
            return "redirect:/assets";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Asset asset = assetService.getAssetById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + id));
            
            AssetDTO assetDTO = new AssetDTO();
            assetDTO.setId(asset.getId());
            assetDTO.setName(asset.getName());
            assetDTO.setBrand(asset.getBrand());
            assetDTO.setSerialNumber(asset.getSerialNumber());
            assetDTO.setDeviceCondition(asset.getDeviceCondition());
            assetDTO.setPurchaseDate(asset.getPurchaseDate());
            assetDTO.setDescription(asset.getDescription());
            assetDTO.setModel(asset.getModel());
            assetDTO.setWarrantyUntil(asset.getWarrantyUntil());
            assetDTO.setLocation(asset.getLocation());
            assetDTO.setLastMaintenanceDate(asset.getLastMaintenanceDate());
            assetDTO.setNextMaintenanceDate(asset.getNextMaintenanceDate());
            assetDTO.setStatus(asset.getStatus().toString());
            
            model.addAttribute("assetDTO", assetDTO);
            
            return "assets/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Asset not found");
            return "redirect:/assets";
        }
    }
    
    @PostMapping("/{id}/edit")
    public String updateAsset(@PathVariable Long id,
                              @Valid @ModelAttribute("assetDTO") AssetDTO assetDTO,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("assetDTO", assetDTO);
            return "assets/form";
        }
        
        try {
            System.out.println("Updating asset ID: " + id);
            Asset asset = assetService.updateAsset(id, assetDTO);
            redirectAttributes.addFlashAttribute("success", 
                "Asset updated successfully: " + asset.getName());
            System.out.println("Asset updated: " + asset.getId());
        } catch (Exception e) {
            System.err.println("Error updating asset: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", 
                "Failed to update asset: " + e.getMessage());
        }
        
        return "redirect:/assets";
    }
    
    @GetMapping("/{id}/delete")
    public String deleteAsset(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Deleting asset ID: " + id);
            assetService.deleteAsset(id);
            redirectAttributes.addFlashAttribute("success", "Asset deleted successfully");
            System.out.println("Asset deleted: " + id);
        } catch (Exception e) {
            System.err.println("Error deleting asset: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", 
                "Failed to delete asset: " + e.getMessage());
        }
        
        return "redirect:/assets";
    }
    
    @GetMapping("/{id}/qr")
    public String generateQRCode(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            String qrCodePath = assetService.generateQRCodeForAsset(id);
            redirectAttributes.addFlashAttribute("success", 
                "QR Code generated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to generate QR Code: " + e.getMessage());
        }
        
        return "redirect:/assets/" + id;
    }
}