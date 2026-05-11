package com.carlog.backend.controller;

import com.carlog.backend.dto.CatalogItemDTO;
import com.carlog.backend.model.CarVersion;
import com.carlog.backend.service.CarCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CarCatalogController {

    private final CarCatalogService catalogService;

    @GetMapping("/brands")
    public List<CatalogItemDTO> getBrands() {
        return catalogService.getAllBrands().stream()
                .map(brand -> new CatalogItemDTO(brand.getId(), brand.getName()))
                .toList();
    }

    @GetMapping("/brands/{brandId}/models")
    public List<CatalogItemDTO> getModels(@PathVariable Long brandId) {
        return catalogService.getModelsByBrand(brandId).stream()
                .map(model -> new CatalogItemDTO(model.getId(), model.getName()))
                .toList();
    }

    @GetMapping("/models/{modelId}/versions")
    public List<CatalogItemDTO> getVersions(@PathVariable Long modelId) {
        return catalogService.getVersionsByModel(modelId).stream()
                .map(v -> new CatalogItemDTO(v.getId(), v.getVersionName()))
                .toList();
    }

    @GetMapping("/versions/{versionId}")
    public ResponseEntity<CarVersion> getVersionDetails(@PathVariable Long versionId) {
        return ResponseEntity.ok(catalogService.getVersionById(versionId));
    }

    @PostMapping("/sync")
    public ResponseEntity<String> triggerSync() {
        catalogService.syncCatalog();
        return ResponseEntity.ok("Sincronización iniciada/completada");
    }
}