package com.carlog.backend.service;

import com.carlog.backend.dto.*;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkOrderLineJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkOrderServiceTest {

    @Mock private UserJpaRepository userJpaRepository;
    @Mock private VehicleJpaRepository vehicleJpaRepository;
    @Mock private WorkOrderJpaRepository workOrderJpaRepository;
    @Mock private WorkOrderLineJpaRepository workOrderLineJpaRepository;
    @Mock private ApplicationEventPublisher eventPublisher;

    @InjectMocks private WorkOrderService workOrderService;

    private User mechanic;
    private User manager;
    private User client;
    private Workshop workshop;
    private Vehicle vehicle;
    private WorkOrder workOrder;
    private WorkOrderLine workOrderLine;
    @Mock private MailService mailService;

    @BeforeEach
    void setUp() {
        workshop = new Workshop();
        workshop.setWorkshopId(1L);
        workshop.setWorkshopName("Taller Central");

        mechanic = new User();
        mechanic.setDni("12345678A");
        mechanic.setEmail("mechanic@test.com");
        mechanic.setRole(Role.MECHANIC);
        mechanic.setWorkshop(workshop);

        manager = new User();
        manager.setDni("87654321B");
        manager.setEmail("manager@test.com");
        manager.setRole(Role.MANAGER);
        manager.setWorkshop(workshop);

        client = new User();
        client.setDni("11111111C");
        client.setEmail("client@test.com");
        client.setRole(Role.CLIENT);

        vehicle = new Vehicle();
        vehicle.setPlate("1234ABC");
        vehicle.setWorkshop(workshop);
        vehicle.setOwner(client);

        workOrder = new WorkOrder();
        workOrder.setId(100L);
        workOrder.setMechanic(mechanic);
        workOrder.setVehicle(vehicle);
        workOrder.setWorkshop(workshop);
        workOrder.setStatus(WorkOrderStatus.PENDING);
        workOrder.setTotalAmount(0.0);

        workOrderLine = new WorkOrderLine();
        workOrderLine.setId(10L);
        workOrderLine.setConcept("Aceite");
        workOrderLine.setWorkOrder(workOrder);
        workOrderLine.setPricePerUnit(50.0);
        workOrderLine.setQuantity(2.0);
        workOrderLine.setIva(21.0);
        workOrderLine.setDiscount(0.0);
        workOrderLine.calculateSubTotal();

        workOrder.addWorkOrderLine(workOrderLine);
    }

    @Test
    void getByEmployee_Success_WhenSameWorkshop() {
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(userJpaRepository.findByDni(mechanic.getDni())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.findByMechanic_Dni(mechanic.getDni())).thenReturn(List.of(workOrder));

        List<NewWorkOrderResponseDTO> result = workOrderService.getByEmployee(mechanic.getDni(), manager.getEmail());

        assertFalse(result.isEmpty());
        verify(workOrderJpaRepository).findByMechanic_Dni(mechanic.getDni());
    }

    @Test
    void getById_Success_WhenWorkerInSameWorkshop() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        NewWorkOrderResponseDTO result = workOrderService.getById(workOrder.getId(), mechanic.getEmail());
        assertNotNull(result);
    }

    @Test
    void add_Success_CreatesWorkOrder() {
        NewWorkOrderDTO dto = new NewWorkOrderDTO("Cambio de aceite", vehicle.getPlate());

        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        NewWorkOrderResponseDTO result = workOrderService.add(dto, mechanic.getEmail(), vehicle.getPlate());

        assertNotNull(result);
        verify(workOrderJpaRepository).save(any(WorkOrder.class));
    }

    @Test
    void addLine_Success_UpdatesStatusToInProgress() {
        NewWorkOrderLineDTO lineDto = new NewWorkOrderLineDTO("Filtro", 1.0, 20.0, 21.0, 0.0);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.addLine(workOrder.getId(), lineDto, mechanic.getEmail());

        assertEquals(WorkOrderStatus.IN_PROGRESS, workOrder.getStatus());
        verify(workOrderJpaRepository).save(workOrder);
    }

    @Test
    void edit_Success_UpdatesStatusAndClosedAt() {
        UpdateWorkOrderDTO updateDto = new UpdateWorkOrderDTO("Notas nuevas", WorkOrderStatus.COMPLETED);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.edit(updateDto, workOrder.getId(), mechanic.getEmail());

        assertEquals(WorkOrderStatus.COMPLETED, workOrder.getStatus());
        assertEquals("Notas nuevas", workOrder.getMechanicNotes());
        assertNotNull(workOrder.getClosedAt());

    }

    @Test
    void updateWorkOrderLine_Success_CalculatesNewTotalAmount() {
        NewWorkOrderLineDTO updateData = new NewWorkOrderLineDTO("Motor", 1.0, 100.0, 21.0, 0.0);

        when(workOrderLineJpaRepository.findById(workOrderLine.getId())).thenReturn(Optional.of(workOrderLine));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.updateWorkOrderLine(workOrder.getId(), workOrderLine.getId(), updateData, mechanic.getEmail());

        assertEquals("Motor", workOrderLine.getConcept());
        assertEquals(121.0, workOrder.getTotalAmount());
    }

    @Test
    void notifyClientForPickup_Success_PublishesEvent() {
        workOrder.setStatus(WorkOrderStatus.COMPLETED);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        workOrderService.notifyClientForPickup(workOrder.getId(), mechanic.getEmail());

        verify(eventPublisher).publishEvent(any(WorkOrderCompletedEvent.class));
    }

    @Test
    void notifyClientForPickup_ThrowsUnauthorized_WhenNotCompleted() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.notifyClientForPickup(workOrder.getId(), mechanic.getEmail())
        );
    }

    @Test
    void notifyClientForPickup_ThrowsUserNotFound_WhenVehicleHasNoOwner() {
        workOrder.setStatus(WorkOrderStatus.COMPLETED);
        vehicle.setOwner(null);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(UserNotFoundException.class, () ->
                workOrderService.notifyClientForPickup(workOrder.getId(), mechanic.getEmail())
        );
    }

    @Test
    void reassignMechanic_Success_AssignsNewMechanic() {
        User newMechanic = new User();
        newMechanic.setDni("88888888M");
        newMechanic.setWorkshop(workshop);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(userJpaRepository.findByDni(newMechanic.getDni())).thenReturn(Optional.of(newMechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.reassignMechanic(workOrder.getId(), newMechanic.getDni(), manager.getEmail());

        assertEquals(newMechanic.getDni(), workOrder.getMechanic().getDni());
        verify(workOrderJpaRepository).save(workOrder);
    }

    @Test
    void delete_Success_DeletesWorkOrder() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));

        workOrderService.delete(workOrder.getId(), manager.getEmail());

        verify(workOrderJpaRepository).delete(workOrder);
    }

    @Test
    void getByVehicle_ThrowsUnauthorized_WhenVehicleInDifferentWorkshop() {
        Workshop anotherWorkshop = new Workshop();
        anotherWorkshop.setWorkshopId(99L);
        vehicle.setWorkshop(anotherWorkshop);

        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.getByVehicle(vehicle.getPlate(), mechanic.getEmail(), Pageable.unpaged())
        );
    }
}