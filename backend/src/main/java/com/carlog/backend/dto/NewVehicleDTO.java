package com.carlog.backend.dto;

import com.carlog.backend.model.Vehicle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record NewVehicleDTO(String plate,
                            String brand,
                            String model,
                            Long kilometers,
                            String engine,
                            int horsePower,
                            int torque,
                            String tires,
                            List<String> images,
                            LocalDate lastMaintenance,
                            Long workshopId,
                            String ownerId) {

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
                v.getOwner() != null ? v.getOwner().getDni(): null
        );
    }
}
