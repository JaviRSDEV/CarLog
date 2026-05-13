package com.carlog.backend.dto;

import com.carlog.backend.model.PaymentStatus;
import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record NewWorkOrderResponseDTO(Long id,
                                      String description,
                                      String mechanicNotes,
                                      WorkOrderStatus status,
                                      LocalDateTime createdAt,
                                      LocalDate closedAt,
                                      NewVehicleDTO vehicle,
                                      String mechanicName,
                                      String mechanicDni,
                                      Long workshopId,
                                      Double totalAmount,
                                      List<NewWorkOrderLineResponseDTO> lines,
                                      PaymentStatus paymentStatus) {

    public static NewWorkOrderResponseDTO of(WorkOrder wo){

        NewVehicleDTO vehicleDTO = null;

        if(wo.getVehicle() != null){
            vehicleDTO = NewVehicleDTO.of(wo.getVehicle());
        }else{
            String historicalBrand = "Vehiculo";
            String historicalModel = "Eliminado";

            if(wo.getHistoricalBrandModel() != null){
                String[] parts = wo.getHistoricalBrandModel().split(" ", 2);
                historicalBrand = parts[0];
                if(parts.length > 1){
                    historicalModel = parts[1];
                }
            }

            vehicleDTO = new NewVehicleDTO(
                    wo.getHistoricalPlate() != null ? wo.getHistoricalPlate() : "DESCONOCIDO",
                    historicalBrand,
                    historicalModel,
                    null,
                    0L,
                    "",
                    0,
                    0,
                    "",
                    null, null, null, null, null, null
            );
        }

        List<NewWorkOrderLineResponseDTO> linesDto = wo.getLines().stream().map(line -> new NewWorkOrderLineResponseDTO(
                line.getId(),
                line.getConcept(),
                line.getQuantity(),
                line.getPricePerUnit(),
                line.getIva(),
                line.getDiscount(),
                line.getSubTotal()
        )).toList();

        return new NewWorkOrderResponseDTO(
                wo.getId(),
                wo.getDescription(),
                wo.getMechanicNotes(),
                wo.getStatus(),
                wo.getCreatedAt(),
                wo.getClosedAt(),
                vehicleDTO,
                wo.getMechanic().getName(),
                wo.getMechanic().getDni(),
                wo.getWorkshop().getWorkshopId(),
                wo.getTotalAmount(),
                linesDto,
                wo.getPaymentStatus()
        );
    }
}
