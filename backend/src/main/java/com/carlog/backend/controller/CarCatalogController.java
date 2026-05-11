package com.carlog.backend.controller;

import com.carlog.backend.dto.CatalogItemDTO;
import com.carlog.backend.repository.CarBrandJpaRepository;
import com.carlog.backend.repository.CarModelJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CarCatalogController {

    private final CarBrandJpaRepository carBrandJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;

    @GetMapping("/brands")
    public List<CatalogItemDTO> getBrands() {
        return carBrandJpaRepository.findAllByOrderByNameAsc()
                .stream()
                .map(brand -> new CatalogItemDTO(brand.getId(), brand.getName()))
                .toList();
    }

    @GetMapping("/models/{brandName}")
    public List<CatalogItemDTO> getModels(@PathVariable String brandName) {
        return carModelJpaRepository.findByBrand_NameIgnoreCaseOrderByNameAsc(brandName)
                .stream()
                .map(model -> new CatalogItemDTO(model.getId(), model.getName()))
                .toList();
    }
}