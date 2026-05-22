package com.carlog.backend.dto;

import com.carlog.backend.model.Vehicle;
import com.carlog.backend.model.WorkOrder;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record VehicleHistoryDTO(
         String orderId,
         String date,
         Long kilometers,
         String workshopName,
         List<String> operations,
         boolean isOwnWorkshop
) {
    public static VehicleHistoryDTO of(WorkOrder order, Long currentWorkshopId) {
        boolean isOwn = order.getWorkshop().getWorkshopId().equals(currentWorkshopId);

        List<String> operationDescriptions = order.getLines().stream()
                .map(line -> line.getConcept())
                .toList();

        return new VehicleHistoryDTO(
                order.getId().toString(),
                order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                order.getKilometers(),
                order.getWorkshop().getWorkshopName(),
                operationDescriptions,
                isOwn
        );
    }
}
