package com.carlog.backend.service;

import com.carlog.backend.dto.*;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.AlertJpaRepository;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkOrderLineJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkOrderService {

    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final WorkOrderLineJpaRepository workOrderLineJpaRepository;
    private final AlertJpaRepository alertJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<NewWorkOrderResponseDTO> getByEmployee(String dni, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        User targetMechanic = userJpaRepository.findByDni(dni)
                .orElseThrow(() -> new UserNotFoundException(dni));

        boolean isSelf = currentUser.getDni().equals(dni);
        boolean isAdmin = currentUser.getRole().isAdmin();
        boolean isSameWorkshop = currentUser.getWorkshop() != null && targetMechanic.getWorkshop() != null &&
                currentUser.getWorkshop().getWorkshopId().equals(targetMechanic.getWorkshop().getWorkshopId());

        if (!isSelf && !isSameWorkshop || isAdmin) {
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

        boolean isWorker = currentUser.getRole().isWorker();
        boolean isAdmin = currentUser.getRole().isAdmin();

        if (isWorker || isAdmin) {
            if (vehicle.getWorkshop() == null || currentUser.getWorkshop() == null ||
                    !vehicle.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
                throw new VehicleNotInWorkshopException("El vehículo no está en el taller");
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

        boolean isWorker = connectedUser.getRole().isWorker();
        boolean isAdmin = connectedUser.getRole().isAdmin();

        if (!isWorker || isAdmin) {
            throw new UnauthorizedActionException("Error: El usuario no tiene permisos para crear una orden de trabajo");
        }

        if (connectedUser.getWorkshop() == null) {
            throw new WorkshopNotAssignedException("Error: El mecánico o manager no dispone de un taller asignado");
        }

        if (referedVehicle.getWorkshop() == null ||
                !referedVehicle.getWorkshop().getWorkshopId().equals(connectedUser.getWorkshop().getWorkshopId())) {
            throw new VehicleNotInWorkshopException("Error: No puedes abrir una nueva orden. El vehículo ya no está en tu taller.");
        }

        var newWorkOrder = WorkOrder.builder()
                .description(dto.description())
                .mechanicNotes(null)
                .status(WorkOrderStatus.PENDING)
                .vehicle(referedVehicle)
                .kilometers(referedVehicle.getKilometers())
                .mechanic(connectedUser)
                .workshop(connectedUser.getWorkshop())
                .totalAmount(0.0)
                .build();

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(newWorkOrder));
    }

    public NewWorkOrderResponseDTO addLine(Long orderId, NewWorkOrderLineDTO lineDto, String email) {
        WorkOrder workOrder = workOrderJpaRepository.findById(orderId).orElseThrow(WorkOrderNotFoundException::new);

        verifyWriteAccess(workOrder, email);

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (workOrder.getStatus() == WorkOrderStatus.COMPLETED && !currentUser.getRole().isAdmin())
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

        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (order.getStatus() == WorkOrderStatus.COMPLETED && !currentUser.getRole().isAdmin())
            throw new ClosedWorkOrderException("No se puede borrar líneas de una orden cerrada");

        order.removeWorkOrderLine(line);
        updateOrderTotal(order);
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
            if (order.getPaymentStatus() == PaymentStatus.PAID && dto.status() == WorkOrderStatus.IN_PROGRESS) {
                throw new ClosedWorkOrderException("Acción bloqueada: No se puede reabrir una orden pagada");
            }

            boolean becomingCompleted = dto.status() == WorkOrderStatus.COMPLETED && order.getStatus() != WorkOrderStatus.COMPLETED;

            order.setStatus(dto.status());
            order.setClosedAt(dto.status() == WorkOrderStatus.COMPLETED ? java.time.LocalDate.now() : null);

            if (becomingCompleted && order.getVehicle() != null && order.getVehicle().getOwner() != null) {
                LocalDate fechaProximoMantenimiento = java.time.LocalDate.now().plusYears(1);

                Alert mantenimientoAnualAlert = Alert.builder()
                        .title("Mantenimiento Anual")
                        .description("Ha transcurrido un año desde tu última revisión en el taller (" + order.getWorkshop().getWorkshopName() + "). Es recomendable realizar un cambio de aceite y filtros de seguridad.")
                        .dueDate(fechaProximoMantenimiento)
                        .vehicle(order.getVehicle())
                        .user(order.getVehicle().getOwner())
                        .notifiedOneWeek(false)
                        .notifiedToday(false)
                        .build();

                alertJpaRepository.save(mantenimientoAnualAlert);
                log.info("Alerta de mantenimiento anual automatizada guardada para el coche con matrícula: {}", order.getVehicle().getPlate());
            }
        }

        if (dto.paymentStatus() != null) {
            order.setPaymentStatus(dto.paymentStatus());
        }

        return NewWorkOrderResponseDTO.of(workOrderJpaRepository.save(order));
    }

    public void notifyClientForPickup(Long orderId, String email){
        WorkOrder order = workOrderJpaRepository.findById(orderId)
                .orElseThrow(WorkOrderNotFoundException::new);

        verifyWriteAccess(order, email);

        if (order.getStatus() != WorkOrderStatus.COMPLETED) {
            throw new WorkOrderLineMismatchException("La orden de trabajo debe estar cerrada");
        }

        if (order.getVehicle() == null || order.getVehicle().getOwner() == null) {
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

        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (order.getStatus() == WorkOrderStatus.COMPLETED && !currentUser.getRole().isAdmin()) {
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

        if (!currentUser.getRole().isWorker() && !currentUser.getRole().isAdmin()) {
            throw new UnauthorizedActionException("Acceso denegado: Solo el personal del taller o administradores pueden modificar la orden.");
        }

        if (currentUser.getRole().isAdmin()) {
            return;
        }

        if (workOrder.getWorkshop() == null || currentUser.getWorkshop() == null ||
                !workOrder.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
            throw new VehicleNotInWorkshopException("El vehículo ya no está en el taller");
        }

        Vehicle vehicle = workOrder.getVehicle();
        if (vehicle != null && (vehicle.getWorkshop() == null ||
                !vehicle.getWorkshop().getWorkshopId().equals(workOrder.getWorkshop().getWorkshopId()))) {
            throw new VehicleNotInWorkshopException("El vehículo ya no está en el taller");
        }
    }

    public Page<VehicleHistoryDTO> getVehicleHistory(String plate, String email, Pageable pageable){
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        boolean isOwner = vehicle.getOwner() != null && vehicle.getOwner().getDni().equals(currentUser.getDni());
        boolean isWorker = currentUser.getRole().isWorker();
        boolean isAdmin = currentUser.getRole().isAdmin();
        Long currentWorkshopId = null;

        if (isAdmin || isOwner) {
            if (isWorker && currentUser.getWorkshop() != null) {
                currentWorkshopId = currentUser.getWorkshop().getWorkshopId();
            }
        }else if (isWorker) {
            if (currentUser.getWorkshop() == null || vehicle.getWorkshop() == null ||
                    !vehicle.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId())) {
                throw new VehicleNotInWorkshopException("Acceso denegado: El vehículo no está en el taller ahora mismo");
            }
            currentWorkshopId = currentUser.getWorkshop().getWorkshopId();
        }else {
            throw new UnauthorizedActionException("Acceso denegado: Este vehículo no es tuyo");
        }

        Page<WorkOrder> workOrderPage = workOrderJpaRepository.findByVehicle_Plate(plate, pageable);
        final Long finalWorkshopId = isWorker ? currentWorkshopId : -1L;

        return workOrderPage.map(order -> {
            if (isOwner || isAdmin || !isWorker) {
                return new VehicleHistoryDTO(
                        order.getId().toString(),
                        order.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        order.getKilometers(),
                        order.getWorkshop() != null ? order.getWorkshop().getWorkshopName() : "Taller Temporal",
                        order.getLines().stream().map(WorkOrderLine::getConcept).toList(),
                        true
                );
            }
            return VehicleHistoryDTO.of(order, finalWorkshopId);
        });
    }
}