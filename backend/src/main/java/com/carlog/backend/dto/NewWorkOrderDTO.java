package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrder;

public record NewWorkOrderDTO(String description,
                              String vehiclePlate) {

    public static NewWorkOrderDTO of(WorkOrder wo){
        return new NewWorkOrderDTO(
                wo.getDescription(),
                wo.getVehicle().getPlate()
        );}
    }
