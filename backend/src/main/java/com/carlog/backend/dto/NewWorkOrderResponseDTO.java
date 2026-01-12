package com.carlog.backend.dto;

import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record NewWorkOrderResponseDTO(String description,
                                      String mechanicNotes,
                                      WorkOrderStatus status,
                                      LocalDateTime createdAt,
                                      LocalDate closedAt,
                                      String vehiclePlate,
                                      String mechanicName,
                                      Long workshopId,
                                      Double totalAmount,
                                      List<NewWorkOrderLineResponseDTO> lines) {

    public static NewWorkOrderResponseDTO of(WorkOrder wo){

        List<NewWorkOrderLineResponseDTO> linesDto = wo.getLines().stream().map(line -> new NewWorkOrderLineResponseDTO(
                line.getConcept(),
                line.getQuantity(),
                line.getPricePerUnit(),
                line.getSubTotal()
        )).toList();

        return new NewWorkOrderResponseDTO(
                wo.getDescription(),
                wo.getMechanicNotes(),
                wo.getStatus(),
                wo.getCreatedAt(),
                wo.getClosedAt(),
                wo.getVehicle().getPlate(),
                wo.getMechanic().getName(),
                wo.getWorkshop().getWorkshopId(),
                wo.getTotalAmount(),
                linesDto
        );
    }
}
