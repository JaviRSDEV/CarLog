package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;

public record UpdateWorkOrderDTO(String mechanicNotes,
                                 WorkOrderStatus status) {

    public static UpdateWorkOrderDTO of(WorkOrder wo){
        return new UpdateWorkOrderDTO(
                wo.getMechanicNotes(),
                wo.getStatus()
        );}
}
