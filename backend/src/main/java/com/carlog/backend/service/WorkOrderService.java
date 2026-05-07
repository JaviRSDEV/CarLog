package com.carlog.backend.service;

import com.carlog.backend.dto.*;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkOrderLineJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final WorkOrderLineJpaRepository workOrderLineJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<NewWorkOrderResponseDTO> getByEmployee(String dni, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        User targetMechanic = userJpaRepository.findByDni(dni)
                .orElseThrow(() -> new UserNotFoundException(dni));

        boolean isSelf = currentUser.getDni().equals(dni);
        boolean isSameWorkshop = currentUser.getWorkshop() != null && targetMechanic.getWorkshop() != null &&
                currentUser.getWorkshop().getWorkshopId().equals(targetMechanic.getWorkshop().getWorkshopId());

        if (!isSelf && !isSameWorkshop) {
            throw new UnauthorizedActionException("Acceso denegado: No puedes ver las órdenes de un mecánico de otro taller.");
        }

        List<WorkOrder> workOrders = workOrderJpaRepository.findByMechanic_Dni(dni);
        return workOrders.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    public NewWorkOrderResponseDTO getById(Long id, String email) {
        WorkOrder workOrder = workOrderJpaRepository.findById(id).orElseThrow(WorkOrderNotFoundException::new);

        verifyReadAccess(workOrder, email);

        return NewWorkOrderResponseDTO.of(workOrder);
    }

    public Page<NewWorkOrderResponseDTO> getByVehicle(String plate, String email, Pageable pageable) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        boolean isWorker = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER ||
                currentUser.getRole() == Role.MECHANIC;

        if (isWorker) {
            if (vehicle.getWorkshop() == null || currentUser.getWorkshop() == null ||
                    !vehicle.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
                throw new VehicleNotInWorkshopException("El vehículo no esta en el taller");
            }
        } else {
            if (vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())) {
                throw new UnauthorizedActionException("Acceso denegado: Este vehículo no es tuyo.");
            }
        }

        return workOrderJpaRepository.findByVehicle_Plate(plate, pageable)
                .map(NewWorkOrderResponseDTO::of);
    }

    public NewWorkOrderResponseDTO add(NewWorkOrderDTO dto, String userEmail, String vehiclePlate) {
        User connectedUser = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        Vehicle referedVehicle = vehicleJpaRepository.findByPlate(vehiclePlate)
                .orElseThrow(() -> new VehicleNotFoundException(vehiclePlate));

        boolean isWorker = connectedUser.getRole() == Role.MECHANIC || connectedUser.getRole() == Role.MANAGER ||
                connectedUser.getRole() == Role.CO_MANAGER || connectedUser.getRole() == Role.DIY;

        if (!isWorker) {
            throw new UnauthorizedActionException("Error: El usuario no tiene permisos para crear una orden de trabajo");
        }

        if (connectedUser.getWorkshop() == null) {
            throw new WorkshopNotAssignedException("Error: El mecánico o manager no dispone de un taller asignado");
        }

        if (connectedUser.getRole() != Role.DIY && (referedVehicle.getWorkshop() == null ||
                !referedVehicle.getWorkshop().getWorkshopId().equals(connectedUser.getWorkshop().getWorkshopId()))) {
            throw new VehicleNotInWorkshopException("Error: No puedes abrir una nueva orden. El vehículo ya no está en tu taller.");
        }

        var newWorkOrder = WorkOrder.builder()
                .description(dto.description())
                .mechanicNotes(null)
                .status(WorkOrderStatus.PENDING)
                .vehicle(referedVehicle)
                .mechanic(connectedUser)
                .workshop(connectedUser.getWorkshop())
                .totalAmount(0.0)
                .build();

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(newWorkOrder));
    }

    public NewWorkOrderResponseDTO addLine(Long orderId, NewWorkOrderLineDTO lineDto, String email) {
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId).orElseThrow(WorkOrderNotFoundException::new);

        verifyWriteAccess(workOrder, email);

        if (workOrder.getStatus() == WorkOrderStatus.COMPLETED)
            throw new ClosedWorkOrderException("No se pueden añadir nuevas líneas a una orden cerrada");

        WorkOrderLine newLine = new WorkOrderLine();
        newLine.setConcept(lineDto.concept());
        newLine.setQuantity(lineDto.quantity());
        newLine.setPricePerUnit(lineDto.pricePerUnit());
        newLine.setIva(lineDto.IVA());
        newLine.setDiscount(lineDto.discount());

        workOrder.addWorkOrderLine(newLine);

        if (workOrder.getStatus() == WorkOrderStatus.PENDING) {
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(workOrder));
    }

    public NewWorkOrderResponseDTO deleteLine(Long orderId, Long lineId, String email) {
        WorkOrderLine line = workOrderLineJpaRepository.findById(lineId)
                .orElseThrow(() -> new WorkOrderLineNotFoundException(lineId.toString()));

        if (!line.getWorkOrder().getId().equals(orderId))
            throw new WorkOrderLineMismatchException("Error: La línea " + lineId + " no pertenece a la orden");

        WorkOrder order = line.getWorkOrder();
        verifyWriteAccess(order, email);

        if (order.getStatus() == WorkOrderStatus.COMPLETED)
            throw new ClosedWorkOrderException("No se puede borrar líneas de una orden cerrada");

        order.removeWorkOrderLine(line);
        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(order));
    }

    @Transactional
    public NewWorkOrderResponseDTO edit(UpdateWorkOrderDTO dto, Long workOrderId, String email) {
        WorkOrder order = workOrderJpaRepository.findById(workOrderId).orElseThrow(WorkOrderNotFoundException::new);

        verifyWriteAccess(order, email);

        if (dto.mechanicNotes() != null) {
            order.setMechanicNotes(dto.mechanicNotes());
        }

        if (dto.status() != null) {
            order.setStatus(dto.status());
            order.setClosedAt(dto.status() == WorkOrderStatus.COMPLETED ? java.time.LocalDate.now() : null);
        }

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(order));
    }

    public void notifyClientForPickup(Long orderId, String email){
        WorkOrder order = workOrderJpaRepository.findById(orderId)
                .orElseThrow(WorkOrderNotFoundException::new);

        verifyWriteAccess(order, email);

        if(order.getStatus() != WorkOrderStatus.COMPLETED){
            throw new WorkOrderLineMismatchException("La orden de trabajo debe estar cerrad");
        }

        if(order.getVehicle() == null || order.getVehicle().getOwner() == null){
            throw new VehicleNotFoundException("El vehículo no tiene un cliente asignado");
        }

        eventPublisher.publishEvent(WorkOrderCompletedEvent.of(order));
    }

    public NewWorkOrderResponseDTO delete(Long workOrderId, String email) {
        WorkOrder workOrder = workOrderJpaRepository.findById(workOrderId).orElseThrow(WorkOrderNotFoundException::new);
        verifyWriteAccess(workOrder, email);
        workOrderJpaRepository.delete(workOrder);
        return NewWorkOrderResponseDTO.of(workOrder);
    }

    public NewWorkOrderResponseDTO updateWorkOrderLine(Long orderId, Long lineId, NewWorkOrderLineDTO lineData, String email) {
        WorkOrderLine line = workOrderLineJpaRepository.findById(lineId)
                .orElseThrow(() -> new WorkOrderLineNotFoundException(lineId.toString()));

        if (!line.getWorkOrder().getId().equals(orderId)) {
            throw new WorkOrderLineMismatchException("Error: la línea no pertenece a la orden " + orderId);
        }

        WorkOrder order = line.getWorkOrder();
        verifyWriteAccess(order, email);

        if (order.getStatus() == WorkOrderStatus.COMPLETED) {
            throw new ClosedWorkOrderException("No se pueden editar líneas de una orden cerrada");
        }

        updateLineData(line, lineData);
        updateOrderTotal(order);

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(order));
    }

    public NewWorkOrderResponseDTO reassignMechanic(Long orderId, String newMechanicId, String email) {
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId).orElseThrow(WorkOrderNotFoundException::new);
        verifyWriteAccess(workOrder, email);

        User newMechanic = userJpaRepository.findByDni(newMechanicId)
                .orElseThrow(() -> new UserNotFoundException(newMechanicId));

        if (!workOrder.getWorkshop().getWorkshopId().equals(newMechanic.getWorkshop().getWorkshopId())) {
            throw new MechanicNotInWorkshopException("Error: El mecánico seleccionado no pertenece a este taller");
        }

        workOrder.setMechanic(newMechanic);
        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(workOrder));
    }

    public List<NewWorkOrderResponseDTO> getWorkOrderByWorkshop(Long workshopId, String email) {
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshopId)) {
            throw new UnauthorizedActionException("Acceso denegado: No puedes ver las órdenes de otro taller.");
        }

        List<WorkOrder> workOrders = workOrderJpaRepository.findByWorkshop_workshopId(workshopId);
        return workOrders.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    private void updateLineData(WorkOrderLine line, NewWorkOrderLineDTO data) {
        line.setConcept(data.concept());
        line.setQuantity(data.quantity());
        line.setPricePerUnit(data.pricePerUnit());
        line.setIva(data.IVA());
        line.setDiscount(data.discount());
        line.calculateSubTotal();
    }

    private void updateOrderTotal(WorkOrder order) {
        double newTotalAmount = order.getLines().stream()
                .mapToDouble(WorkOrderLine::getSubTotal)
                .sum();
        order.setTotalAmount(Math.round(newTotalAmount * 100.0) / 100.0);
    }

    private void verifyReadAccess(WorkOrder workOrder, String email) {
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        boolean isWorker = currentUser.getRole().isWorker();

        if (isWorker) {
            if (workOrder.getWorkshop() == null || currentUser.getWorkshop() == null ||
                    !workOrder.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
                throw new UnauthorizedActionException("Acceso denegado: Esta orden pertenece a otro taller.");
            }
        } else {
            if (workOrder.getVehicle() == null || workOrder.getVehicle().getOwner() == null ||
                    !workOrder.getVehicle().getOwner().getDni().equals(currentUser.getDni())) {
                throw new UnauthorizedActionException("Acceso denegado: Esta orden no pertenece a tu vehículo.");
            }
        }
    }

    private void verifyWriteAccess(WorkOrder workOrder, String email) {
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getRole().isWorker()) {
            throw new UnauthorizedActionException("Acceso denegado: Solo el personal del taller puede modificar la orden.");
        }

        if (workOrder.getWorkshop() == null || currentUser.getWorkshop() == null ||
                !workOrder.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
            throw new VehicleNotInWorkshopException("El vehículo ya no esta en el taller");
        }

        Vehicle vehicle = workOrder.getVehicle();
        if (vehicle != null && (vehicle.getWorkshop() == null ||
                !vehicle.getWorkshop().getWorkshopId().equals(workOrder.getWorkshop().getWorkshopId()))) {
            throw new VehicleNotInWorkshopException("El vehículo ya no esta en el taller");
        }
    }
}