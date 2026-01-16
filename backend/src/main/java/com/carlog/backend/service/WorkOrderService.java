package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.VehicleNotFoundException;
import com.carlog.backend.error.WorkOrderNotFoundException;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkOrderLineJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final WorkOrderLineJpaRepository workOrderLineJpaRepository;

    public List<WorkOrder> getAll(){
        var result = workOrderJpaRepository.findAll();
        if(result.isEmpty()) throw new WorkOrderNotFoundException();
        return result;
    }

    public List<WorkOrder> getByEmployee(String dni){
        List<WorkOrder> workOrders = workOrderJpaRepository.findByMechanic_Dni(dni);
        if(workOrders.isEmpty()) throw new WorkOrderNotFoundException();
        return workOrders;
    }

    public List<WorkOrder> getByVehicle(String plate){
        List<WorkOrder> workOrders = workOrderJpaRepository.findByVehicle_Plate(plate);
        if(workOrders.isEmpty()) throw new WorkOrderNotFoundException();
        return workOrders;
    }

    public NewWorkOrderResponseDTO add(NewWorkOrderDTO dto, String userEmail, String vehiclePlate){
        User connectedUser = userJpaRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        Vehicle referedVehicle = vehicleJpaRepository.findByPlate(vehiclePlate).orElseThrow(() -> new VehicleNotFoundException(vehiclePlate));

        boolean isWorker = connectedUser.getRole() == Role.MECHANIC || connectedUser.getRole() == Role.MANAGER || connectedUser.getRole() == Role.CO_MANAGER || connectedUser.getRole() == Role.DIY;
        if(!isWorker) {
            throw new RuntimeException("Error: El usuario no tiene permisos para crear una orden de trabajo");
        }

        if (connectedUser.getWorkshop() == null && connectedUser.getRole() != Role.DIY) {
            throw new RuntimeException("Error: El mecanico o manager no dispone de un taller asignado");
        }

        var newWorkOrder = WorkOrder.builder().description(dto.description()).mechanicNotes(null).status(WorkOrderStatus.PENDING).vehicle(referedVehicle).mechanic(connectedUser).workshop(connectedUser.getWorkshop()).totalAmount(0.0).build();
        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(newWorkOrder));
    }

    public NewWorkOrderResponseDTO addLine(Long orderId, NewWorkOrderLineDTO lineDto){
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId).orElseThrow(() -> new WorkOrderNotFoundException());

        if(workOrder.getStatus() == WorkOrderStatus.COMPLETED)
            throw new RuntimeException("No se pueden añadir nuevas lineas a una orden cerrada");

        WorkOrderLine newLine = new WorkOrderLine();
        newLine.setConcept(lineDto.concept());
        newLine.setQuantity(lineDto.quantity());
        newLine.setPricePerUnit(lineDto.pricePerUnit());
        newLine.setIVA(lineDto.IVA());
        newLine.setDiscount(lineDto.discount());

        workOrder.addWorkOrderLine(newLine);

        if(workOrder.getStatus() == WorkOrderStatus.PENDING){
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }

        WorkOrder updateWorkOrder = workOrderJpaRepository.save(workOrder);

        return NewWorkOrderResponseDTO.of(updateWorkOrder);
    }

    public NewWorkOrderResponseDTO deleteLine(Long orderId, Long lineId){
        WorkOrderLine line = workOrderLineJpaRepository.findById(lineId).orElseThrow(() -> new RuntimeException("Linea no encontrada"));

        if(!line.getWorkOrder().getId().equals(orderId))
            throw new RuntimeException("Error: La línea " + lineId + " no pertenece a la orden");

        WorkOrder order = line.getWorkOrder();

        if(order.getStatus() == WorkOrderStatus.COMPLETED)
            throw new RuntimeException("No se puede borrar líneas de una orden cerrada");

        order.removeWorkOrderLine(line);

        WorkOrder updatedOrder = workOrderJpaRepository.save(order);

        return NewWorkOrderResponseDTO.of(updatedOrder);
    }

    public NewWorkOrderResponseDTO edit(UpdateWorkOrderDTO dto, Long workOrderId){
        WorkOrder order = workOrderJpaRepository.findById(workOrderId).orElseThrow(() -> new WorkOrderNotFoundException());

        if(dto.mechanicNotes() != null)
            order.setMechanicNotes(dto.mechanicNotes());

        if(dto.status() != null) {
            order.setStatus(dto.status());

            if(dto.status() == WorkOrderStatus.COMPLETED){
                order.setClosedAt(java.time.LocalDate.now());
            }else{
                order.setClosedAt(null);
            }
        }

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(order));
    }

    public NewWorkOrderResponseDTO delete(Long workOrderId){
        WorkOrder workOrder = workOrderJpaRepository.findById(workOrderId).orElseThrow(() -> new WorkOrderNotFoundException());
        workOrderJpaRepository.delete(workOrder);

        return NewWorkOrderResponseDTO.of(workOrder);
    }


}
