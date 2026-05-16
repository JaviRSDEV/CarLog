package com.carlog.backend.dto;

import com.carlog.backend.model.CarBrand;

public record CarBrandDTO(Long id, String name) {
    public static CarBrandDTO of(CarBrand brand){
        return new CarBrandDTO(brand.getId(), brand.getName());
    }
}
