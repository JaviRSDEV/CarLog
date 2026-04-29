package com.carlog.backend.dto;

import com.carlog.backend.model.Vehicle;
import com.carlog.backend.model.WorkOrder;

public record VehicleAdmissionEvent(
        String clientEmail,
        String clientName,
        String vehiclePlate,
        String workshopName
) {
    public static VehicleAdmissionEvent of(Vehicle vehicle) {
        return new VehicleAdmissionEvent(
                vehicle.getOwner().getEmail(),
                vehicle.getOwner().getName(),
                vehicle.getPlate(),
                vehicle.getPendingWorkshop().getWorkshopName()
        );
    }
}
