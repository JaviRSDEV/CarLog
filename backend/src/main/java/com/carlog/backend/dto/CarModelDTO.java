package com.carlog.backend.dto;

import com.carlog.backend.model.CarModel;

public record CarModelDTO(Long id, String name, String brandName) {
    public static CarModelDTO of(CarModel model){
        return new CarModelDTO(
                model.getId(),
                model.getName(),
                model.getBrand().getName()
        );
    }
}
