package com.carlog.backend.dto;

import com.carlog.backend.model.Vehicle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record NewVehicleDTO(
                            @NotBlank(message = "La matrícula no puede estar vacía")
                            String plate,
                            @NotBlank(message = "La marca es obligatoria")
                            String brand,
                            @NotBlank(message = "El modelo es obligatorio")
                            String model,
                            @NotNull @Min(0)
                            Long kilometers,
                            String engine,
                            @Min(0)
                            int horsePower,
                            @Min(0)
                            int torque,
                            String tires,
                            List<String> images,
                            LocalDate lastMaintenance,
                            Long workshopId,
                            String ownerId,
                            Long pendingWorkshopId,
                            String pendingWorkshopName) {

    public static NewVehicleDTO of(Vehicle v){
        return new NewVehicleDTO(
                v.getPlate(),
                v.getBrand(),
                v.getModel(),
                v.getKilometers(),
                v.getEngine(),
                v.getHorsePower(),
                v.getTorque(),
                v.getTires(),
                v.getImages() != null ? new ArrayList<>(v.getImages()) : new ArrayList<>(),
                v.getLastMaintenance(),
                v.getWorkshop() != null ? v.getWorkshop().getWorkshopId(): null,
                v.getOwner() != null ? v.getOwner().getDni(): null,
                v.getPendingWorkshop() != null ? v.getPendingWorkshop().getWorkshopId(): null,
                v.getPendingWorkshop() != null ? v.getPendingWorkshop().getWorkshopName(): null
        );
    }
}
