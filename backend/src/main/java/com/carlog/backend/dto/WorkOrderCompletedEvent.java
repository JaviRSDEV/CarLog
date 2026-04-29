package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrder;

public record WorkOrderCompletedEvent(
        String clientEmail,
        String clientName,
        String vehiclePlate
) {
    public static WorkOrderCompletedEvent of(WorkOrder workOrder) {
        return new WorkOrderCompletedEvent(
                workOrder.getVehicle().getOwner().getEmail(),
                workOrder.getVehicle().getOwner().getName(),
                workOrder.getVehicle().getPlate()
        );
    }
}