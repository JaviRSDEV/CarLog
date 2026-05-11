package com.carlog.backend.dto;

import com.carlog.backend.model.CarVersion;

public record CarVersionDTO(
        Long id,
        String versionName,
        String engineCode,
        String engineType,
        String fuelType,
        Integer powerCv,
        Integer torque,
        Integer yearStart,
        Integer yearEnd
) {
    public static CarVersionDTO of(CarVersion cv){
        return new CarVersionDTO(
                cv.getId(),
                cv.getVersionName(),
                cv.getEngineCode(),
                cv.getEngineType(),
                cv.getFuelType(),
                cv.getPowerCv(),
                cv.getTorque(),
                cv.getYearStart(),
                cv.getYearEnd()
        );}
}
