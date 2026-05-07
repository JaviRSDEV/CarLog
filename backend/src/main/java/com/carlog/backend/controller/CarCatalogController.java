package com.carlog.backend.controller;

import com.carlog.backend.model.CarBrand;
import com.carlog.backend.model.CarModel;
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
    public List<String> getBrands(){
        return carBrandJpaRepository.findAll().stream().map(CarBrand::getName).toList();
    }

    @GetMapping("/models/{brandName}")
    public List<String> getModels(@PathVariable String brandName){
        return carModelJpaRepository.findByBrand_NameIgnoreCase(brandName)
                .stream().map(CarModel::getName).toList();
    }
}
