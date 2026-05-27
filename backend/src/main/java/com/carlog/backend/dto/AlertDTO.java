package com.carlog.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlertDTO(
        Long id,

        @NotBlank(message = "El título del aviso es obligatorio")
        String title,

        String description,

        @NotNull(message = "La fecha límite es obligatoria")
        LocalDate dueDate,

        @NotBlank(message = "La matrícula del vehículo es obligatoria")
        String vehiclePlate
) {
    public static AlertDTO of(com.carlog.backend.model.Alert alert) {
        return new AlertDTO(
                alert.getId(),
                alert.getTitle(),
                alert.getDescription(),
                alert.getDueDate(),
                alert.getVehicle().getPlate()
        );
    }
}
