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

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private VehicleJpaRepository vehicleJpaRepository;
    @Mock
    private WorkOrderJpaRepository workOrderJpaRepository;
    @Mock
    private WorkOrderLineJpaRepository workOrderLineJpaRepository;

    @InjectMocks
    private WorkOrderService workOrderService;

    private User mechanic;
    private User manager;
    private User client;
    private Workshop workshop;
    private Vehicle vehicle;
    private WorkOrder workOrder;
    private WorkOrderLine workOrderLine;

    @BeforeEach
    void setUp() {
        workshop = new Workshop();
        workshop.setWorkshopId(1L);

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
    void getByEmployee_ThrowsUnauthorized_WhenDifferentWorkshop() {
        Workshop anotherWorkshop = new Workshop();
        anotherWorkshop.setWorkshopId(2L);
        manager.setWorkshop(anotherWorkshop);

        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(userJpaRepository.findByDni(mechanic.getDni())).thenReturn(Optional.of(mechanic));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.getByEmployee(mechanic.getDni(), manager.getEmail())
        );
    }

    @Test
    void getById_Success_WhenWorkerInSameWorkshop() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        NewWorkOrderResponseDTO result = workOrderService.getById(workOrder.getId(), mechanic.getEmail());
        assertNotNull(result);
    }

    @Test
    void getById_ThrowsUnauthorized_WhenClientNotOwner() {
        User anotherClient = new User();
        anotherClient.setDni("99999999Z");
        anotherClient.setEmail("other@test.com");
        anotherClient.setRole(Role.CLIENT);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(anotherClient.getEmail())).thenReturn(Optional.of(anotherClient));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.getById(workOrder.getId(), anotherClient.getEmail())
        );
    }

    @Test
    void getById_ThrowsWorkOrderNotFound() {
        when(workOrderJpaRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(WorkOrderNotFoundException.class, () ->
                workOrderService.getById(999L, mechanic.getEmail())
        );
    }

    @Test
    void getByVehicle_Success_ForWorkshopWorker() {
        Page<WorkOrder> page = new PageImpl<>(List.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(workOrderJpaRepository.findByVehicle_Plate(eq(vehicle.getPlate()), any(Pageable.class))).thenReturn(page);

        Page<NewWorkOrderResponseDTO> result = workOrderService.getByVehicle(vehicle.getPlate(), mechanic.getEmail(), Pageable.unpaged());
        assertFalse(result.isEmpty());
    }

    @Test
    void getByVehicle_ThrowsUnauthorized_WhenClientNotOwner() {
        User anotherClient = new User();
        anotherClient.setDni("99999999Z");
        anotherClient.setRole(Role.CLIENT);
        anotherClient.setEmail("other@test.com");

        when(userJpaRepository.findByEmail(anotherClient.getEmail())).thenReturn(Optional.of(anotherClient));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.getByVehicle(vehicle.getPlate(), anotherClient.getEmail(), Pageable.unpaged())
        );
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
    void add_ThrowsUnauthorized_WhenUserIsClient() {
        NewWorkOrderDTO dto = new NewWorkOrderDTO("Frenos", vehicle.getPlate());
        when(userJpaRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.add(dto, client.getEmail(), vehicle.getPlate())
        );
    }

    @Test
    void add_ThrowsWorkshopNotAssigned_WhenWorkerHasNoWorkshop() {
        NewWorkOrderDTO dto = new NewWorkOrderDTO("Frenos", vehicle.getPlate());
        mechanic.setWorkshop(null);

        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));

        assertThrows(WorkshopNotAssignedException.class, () ->
                workOrderService.add(dto, mechanic.getEmail(), vehicle.getPlate())
        );
    }

    @Test
    void add_ThrowsVehicleNotInWorkshop_WhenVehicleInDifferentWorkshop() {
        NewWorkOrderDTO dto = new NewWorkOrderDTO("Reparación", vehicle.getPlate());
        Workshop differentWorkshop = new Workshop();
        differentWorkshop.setWorkshopId(99L);
        vehicle.setWorkshop(differentWorkshop);

        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));

        assertThrows(VehicleNotInWorkshopException.class, () ->
                workOrderService.add(dto, mechanic.getEmail(), vehicle.getPlate())
        );
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
    void addLine_ThrowsClosedWorkOrderException() {
        workOrder.setStatus(WorkOrderStatus.COMPLETED);
        NewWorkOrderLineDTO lineDto = new NewWorkOrderLineDTO("Filtro", 1.0, 20.0, 21.0, 0.0);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(ClosedWorkOrderException.class, () ->
                workOrderService.addLine(workOrder.getId(), lineDto, mechanic.getEmail())
        );
    }

    @Test
    void deleteLine_Success_RemovesLine() {
        when(workOrderLineJpaRepository.findById(workOrderLine.getId())).thenReturn(Optional.of(workOrderLine));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.deleteLine(workOrder.getId(), workOrderLine.getId(), mechanic.getEmail());

        assertTrue(workOrder.getLines().isEmpty());
        verify(workOrderJpaRepository).save(workOrder);
    }

    @Test
    void deleteLine_ThrowsWorkOrderLineMismatchException() {
        WorkOrder anotherOrder = new WorkOrder();
        anotherOrder.setId(999L);
        workOrderLine.setWorkOrder(anotherOrder);

        when(workOrderLineJpaRepository.findById(workOrderLine.getId())).thenReturn(Optional.of(workOrderLine));

        assertThrows(WorkOrderLineMismatchException.class, () ->
                workOrderService.deleteLine(workOrder.getId(), workOrderLine.getId(), mechanic.getEmail())
        );
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
    void delete_Success_DeletesWorkOrder() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));

        workOrderService.delete(workOrder.getId(), manager.getEmail());

        verify(workOrderJpaRepository).delete(workOrder);
    }

    @Test
    void delete_ThrowsUnauthorized_WhenUserIsClient() {
        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.delete(workOrder.getId(), client.getEmail())
        );
        verify(workOrderJpaRepository, never()).delete(any());
    }

    @Test
    void updateWorkOrderLine_Success_CalculatesNewTotalAmount() {
        NewWorkOrderLineDTO updateData = new NewWorkOrderLineDTO("Motor", 1.0, 100.0, 21.0, 0.0);

        when(workOrderLineJpaRepository.findById(workOrderLine.getId())).thenReturn(Optional.of(workOrderLine));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));
        when(workOrderJpaRepository.save(any(WorkOrder.class))).thenReturn(workOrder);

        workOrderService.updateWorkOrderLine(workOrder.getId(), workOrderLine.getId(), updateData, mechanic.getEmail());

        assertEquals("Motor", workOrderLine.getConcept());
        assertEquals(100.0, workOrderLine.getPricePerUnit());
        assertEquals(121.0, workOrder.getTotalAmount());
        verify(workOrderJpaRepository).save(workOrder);
    }

    @Test
    void updateWorkOrderLine_ThrowsClosedWorkOrderException() {
        workOrder.setStatus(WorkOrderStatus.COMPLETED);
        NewWorkOrderLineDTO updateData = new NewWorkOrderLineDTO("Motor", 1.0, 100.0, 21.0, 0.0);

        when(workOrderLineJpaRepository.findById(workOrderLine.getId())).thenReturn(Optional.of(workOrderLine));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(ClosedWorkOrderException.class, () ->
                workOrderService.updateWorkOrderLine(workOrder.getId(), workOrderLine.getId(), updateData, mechanic.getEmail())
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
    void reassignMechanic_ThrowsMechanicNotInWorkshopException() {
        User externalMechanic = new User();
        externalMechanic.setDni("99999999Z");
        Workshop externalWorkshop = new Workshop();
        externalWorkshop.setWorkshopId(55L);
        externalMechanic.setWorkshop(externalWorkshop);

        when(workOrderJpaRepository.findById(workOrder.getId())).thenReturn(Optional.of(workOrder));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(userJpaRepository.findByDni(externalMechanic.getDni())).thenReturn(Optional.of(externalMechanic));

        assertThrows(MechanicNotInWorkshopException.class, () ->
                workOrderService.reassignMechanic(workOrder.getId(), externalMechanic.getDni(), manager.getEmail())
        );
    }

    @Test
    void getWorkOrderByWorkshop_Success_ReturnsList() {
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(workOrderJpaRepository.findByWorkshop_workshopId(workshop.getWorkshopId())).thenReturn(List.of(workOrder));

        List<NewWorkOrderResponseDTO> result = workOrderService.getWorkOrderByWorkshop(workshop.getWorkshopId(), manager.getEmail());

        assertFalse(result.isEmpty());
        verify(workOrderJpaRepository).findByWorkshop_workshopId(workshop.getWorkshopId());
    }

    @Test
    void getWorkOrderByWorkshop_ThrowsUnauthorized_WhenWorkshopDiffers() {
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));

        assertThrows(UnauthorizedActionException.class, () ->
                workOrderService.getWorkOrderByWorkshop(99L, manager.getEmail())
        );
    }
}