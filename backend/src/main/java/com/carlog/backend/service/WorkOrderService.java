package com.carlog.backend.service;

import com.carlog.backend.dto.*;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final WorkOrderLineJpaRepository workOrderLineJpaRepository;

    public List<NewWorkOrderResponseDTO> getAll(){
        var result = workOrderJpaRepository.findAll();
        return result.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    public List<NewWorkOrderResponseDTO> getByEmployee(String dni){
        List<WorkOrder> workOrders = workOrderJpaRepository.findByMechanic_Dni(dni);
        return workOrders.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    public NewWorkOrderResponseDTO getById(Long id, String email){
        WorkOrder workOrder = workOrderJpaRepository.findById(id).orElseThrow(() -> new WorkOrderNotFoundException());

        verifyReadAccess(workOrder, email);

        return NewWorkOrderResponseDTO.of(workOrder);
    }

    public List<NewWorkOrderResponseDTO> getByVehicle(String plate, String email){
        List<WorkOrder> workOrders = workOrderJpaRepository.findByVehicle_Plate(plate);
        if(workOrders.isEmpty()) throw new WorkOrderNotFoundException();

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isWorker = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER ||
                currentUser.getRole() == Role.MECHANIC;

        return workOrders.stream()
                .filter(wo -> {
                    if (isWorker) {
                        return wo.getWorkshop() != null && currentUser.getWorkshop() != null &&
                                wo.getWorkshop().getWorkshopId() == (currentUser.getWorkshop().getWorkshopId());
                    } else {
                        return wo.getVehicle() != null && wo.getVehicle().getOwner() != null &&
                                wo.getVehicle().getOwner().getDni().equals(currentUser.getDni());
                    }
                })
                .map(NewWorkOrderResponseDTO::of)
                .collect(Collectors.toList());
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

    public NewWorkOrderResponseDTO addLine(Long orderId, NewWorkOrderLineDTO lineDto, String email){
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId).orElseThrow(() -> new WorkOrderNotFoundException());

        verifyWriteAccess(workOrder, email);

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

    public NewWorkOrderResponseDTO deleteLine(Long orderId, Long lineId, String email){
        WorkOrderLine line = workOrderLineJpaRepository.findById(lineId).orElseThrow(() -> new RuntimeException("Linea no encontrada"));

        if(!line.getWorkOrder().getId().equals(orderId))
            throw new RuntimeException("Error: La línea " + lineId + " no pertenece a la orden");

        WorkOrder order = line.getWorkOrder();

        verifyWriteAccess(order, email);

        if(order.getStatus() == WorkOrderStatus.COMPLETED)
            throw new RuntimeException("No se puede borrar líneas de una orden cerrada");

        order.removeWorkOrderLine(line);
        WorkOrder updatedOrder = workOrderJpaRepository.save(order);
        return NewWorkOrderResponseDTO.of(updatedOrder);
    }

    public NewWorkOrderResponseDTO edit(UpdateWorkOrderDTO dto, Long workOrderId, String email){
        WorkOrder order = workOrderJpaRepository.findById(workOrderId).orElseThrow(() -> new WorkOrderNotFoundException());

        verifyWriteAccess(order, email);

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

    public NewWorkOrderResponseDTO delete(Long workOrderId, String email){
        WorkOrder workOrder = workOrderJpaRepository.findById(workOrderId).orElseThrow(() -> new WorkOrderNotFoundException());

        verifyWriteAccess(workOrder, email);

        workOrderJpaRepository.delete(workOrder);
        return NewWorkOrderResponseDTO.of(workOrder);
    }

    public NewWorkOrderResponseDTO updateWorkOrderLine(Long orderId, Long lineId, NewWorkOrderLineDTO lineData, String email) {
        WorkOrderLine line = workOrderLineJpaRepository.findById(lineId)
                .orElseThrow(() -> new RuntimeException("Linea no encontrada"));

        if(!line.getWorkOrder().getId().equals(orderId)){
            throw new RuntimeException("Error: la línea " + lineId + " no pertenece a la orden " + orderId);
        }

        WorkOrder order = line.getWorkOrder();
        verifyWriteAccess(order, email);

        if(order.getStatus() == WorkOrderStatus.COMPLETED){
            throw new RuntimeException("No se pueden editar líneas de una orden cerrada");
        }

        line.setConcept(lineData.concept());
        line.setQuantity(lineData.quantity());
        line.setPricePerUnit(lineData.pricePerUnit());
        line.setIVA(lineData.IVA());
        line.setDiscount(lineData.discount());

        line.calculateSubTotal();

        double newTotalAmount = order.getLines().stream()
                .mapToDouble(WorkOrderLine::getSubTotal)
                .sum();

        newTotalAmount = Math.round(newTotalAmount * 100.0) /100.0;
        order.setTotalAmount(newTotalAmount);

        WorkOrder updatedOrder = workOrderJpaRepository.save(order);
        return NewWorkOrderResponseDTO.of(updatedOrder);
    }

    public NewWorkOrderResponseDTO reassignMechanic(Long orderId, String newMechanicId, String email){
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId)
                .orElseThrow(() -> new WorkOrderNotFoundException());

        verifyWriteAccess(workOrder, email);

        User newMechanic = userJpaRepository.findByDni(newMechanicId)
                .orElseThrow(() -> new UserNotFoundException(newMechanicId));

        if(workOrder.getWorkshop().getWorkshopId() != newMechanic.getWorkshop().getWorkshopId()){
            throw new RuntimeException("Error: El mecánico seleccionado no pertenece a este taller");
        }

        workOrder.setMechanic(newMechanic);
        WorkOrder updatedOrder = workOrderJpaRepository.save(workOrder);
        return NewWorkOrderResponseDTO.of(updatedOrder);
    }

    public List<NewWorkOrderResponseDTO> getWorkOrderByWorkshop(Long workshopId){
        List<WorkOrder> workOrders = workOrderJpaRepository.findByWorkshop_workshopId(workshopId);
        return workOrders.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    private void verifyReadAccess(WorkOrder workOrder, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isWorker = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER ||
                currentUser.getRole() == Role.MECHANIC;

        if (isWorker) {
            System.out.println("==== DEBUG SEGURIDAD ====");
            System.out.println("Email usuario: " + email);
            System.out.println("ID Taller del Usuario: " + (currentUser.getWorkshop() != null ? currentUser.getWorkshop().getWorkshopId() : "NULL"));
            System.out.println("ID Taller de la Orden: " + (workOrder.getWorkshop() != null ? workOrder.getWorkshop().getWorkshopId() : "NULL"));
            System.out.println("=========================");
            if (workOrder.getWorkshop() == null || currentUser.getWorkshop() == null ||
                    workOrder.getWorkshop().getWorkshopId() != (currentUser.getWorkshop().getWorkshopId())) {
                throw new RuntimeException("Acceso denegado: Esta orden pertenece a otro taller.");
            }
        } else {
            if (workOrder.getVehicle() == null || workOrder.getVehicle().getOwner() == null ||
                    !workOrder.getVehicle().getOwner().getDni().equals(currentUser.getDni())) {
                throw new RuntimeException("Acceso denegado: Esta orden no pertenece a tu vehículo.");
            }
        }
    }

    private void verifyWriteAccess(WorkOrder workOrder, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isWorker = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER ||
                currentUser.getRole() == Role.MECHANIC;

        if (!isWorker) {
            throw new RuntimeException("Acceso denegado: Solo el personal del taller puede modificar la orden.");
        }

        if (workOrder.getWorkshop() == null || currentUser.getWorkshop() == null ||
                workOrder.getWorkshop().getWorkshopId() != (currentUser.getWorkshop().getWorkshopId())) {
            throw new RuntimeException("Acceso denegado: No puedes modificar órdenes de otro taller.");
        }
    }
}