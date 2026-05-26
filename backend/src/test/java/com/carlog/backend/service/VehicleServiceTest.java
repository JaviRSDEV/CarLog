package com.carlog.backend.service;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.dto.VehicleAdmissionEvent;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock private UserJpaRepository userJpaRepository;
    @Mock private VehicleJpaRepository vehicleJpaRepository;
    @Mock private WorkshopJpaRepository workshopJpaRepository;
    @Mock private WorkOrderJpaRepository workOrderJpaRepository;
    @Mock private SimpMessagingTemplate messagingTemplate;
    @Mock private Cloudinary cloudinary;
    @Mock private Uploader uploader;
    @Mock private ApplicationEventPublisher eventPublisher;
    @Mock private CarBrandJpaRepository brandJpaRepository;
    @Mock private CarModelJpaRepository modelJpaRepository;
    @Mock private CarVersionJpaRepository versionJpaRepository;

    @InjectMocks private VehicleService vehicleService;

    @Captor private ArgumentCaptor<Vehicle> vehicleCaptor;

    private User clientUser;
    private User mechanicUser;
    private Workshop workshop;
    private Vehicle vehicle;
    private Pageable pageable;

    @BeforeEach
    void setup() {
        pageable = PageRequest.of(0, 10);
        workshop = Workshop.builder().workshopId(1L).workshopName("Taller Test").build();
        clientUser = User.builder().dni("11111111A").email("client@test.com").role(Role.CLIENT).name("Javier").build();
        mechanicUser = User.builder().dni("22222222B").email("mechanic@test.com").role(Role.MECHANIC).workshop(workshop).build();

        vehicle = Vehicle.builder()
                .plate("1234-ABC")
                .brand("Citroen")
                .model("C3")
                .engine("1.4 HDi")
                .owner(clientUser)
                .workshop(workshop)
                .images(new ArrayList<>())
                .build();

        lenient().when(brandJpaRepository.save(any(CarBrand.class))).thenAnswer(invocation -> {
            CarBrand b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        lenient().when(modelJpaRepository.save(any(CarModel.class))).thenAnswer(invocation -> {
            CarModel m = invocation.getArgument(0);
            m.setId(1L);
            return m;
        });

        lenient().when(versionJpaRepository.save(any(CarVersion.class))).thenAnswer(invocation -> {
            CarVersion v = invocation.getArgument(0);
            v.setId(1L);
            return v;
        });
    }

    @Test
    void addVehicleWhenUserIsClient() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.empty());
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        NewVehicleDTO result = vehicleService.add(NewVehicleDTO.of(vehicle), clientUser.getEmail());

        assertNotNull(result);
        assertEquals("1234-ABC", result.plate());
        verify(vehicleJpaRepository).save(any(Vehicle.class));
    }

    @Test
    void addVehicleWhenUserIsMechanic() {
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.empty());
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        NewVehicleDTO dto = NewVehicleDTO.of(vehicle);
        vehicleService.add(dto, mechanicUser.getEmail());

        verify(vehicleJpaRepository).save(vehicleCaptor.capture());
        Vehicle vehicleStored = vehicleCaptor.getValue();

        assertNotNull(vehicleStored.getWorkshop());
        assertEquals("1234-ABC", vehicleStored.getPlate());
        assertEquals(mechanicUser.getWorkshop().getWorkshopId(), vehicleStored.getWorkshop().getWorkshopId());
    }

    @Test
    void addVehicleWithBase64Images_UploadsToCloudinary() throws Exception {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.empty());
        when(cloudinary.uploader()).thenReturn(uploader);

        var mockResponse = Collections.singletonMap("secure_url", "https://cloudinary.com/fake-url.png");
        when(uploader.upload(anyString(), anyMap())).thenReturn(mockResponse);
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        List<String> base64Images = List.of("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...");
        NewVehicleDTO editDto = new NewVehicleDTO(
                "1234-ABC", "Citroen", "C3", null, 150000L,
                "1.4 HDi", 70, 160, "Michelin",
                base64Images, null, null, null, null, null, null
        );

        vehicleService.add(editDto, clientUser.getEmail());

        verify(uploader, times(1)).upload(eq(base64Images.get(0)), anyMap());
        verify(vehicleJpaRepository).save(any(Vehicle.class));
    }

    @Test
    void addVehicleWhenUserDoestNotExist() {
        String fakeEmail = "fantasma@test.com";
        when(userJpaRepository.findByEmail(fakeEmail)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                vehicleService.add(NewVehicleDTO.of(vehicle), fakeEmail)
        );
        verify(vehicleJpaRepository, never()).save(any());
    }

    @Test
    void getMyVehicles_AsClient_ReturnsOwnedVehicles() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByOwner_Dni(clientUser.getDni(), pageable))
                .thenReturn(new PageImpl<>(List.of(vehicle)));

        Page<NewVehicleDTO> result = vehicleService.getMyVehicles(clientUser.getEmail(), pageable);

        assertFalse(result.isEmpty());
        assertEquals("1234-ABC", result.getContent().get(0).plate());
        verify(vehicleJpaRepository).findByOwner_Dni(clientUser.getDni(), pageable);
    }

    @Test
    void getByWorkshop_UserBelongsToWorkshop_ReturnsVehicle() {
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));
        when(vehicleJpaRepository.findByWorkshop_WorkshopId(workshop.getWorkshopId(), pageable))
                .thenReturn(new PageImpl<>(List.of(vehicle)));

        Page<NewVehicleDTO> result = vehicleService.getByWorkshop(workshop.getWorkshopId(), mechanicUser.getEmail(), pageable);

        assertFalse(result.isEmpty());
    }

    @Test
    void getByWorkshop_UserDoesNotBelongToWorkshop_ThrowsUnauthorized() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        assertThrows(UnauthorizedActionException.class, () ->
                vehicleService.getByWorkshop(1L, clientUser.getEmail(), pageable)
        );
    }

    @Test
    void getByPlate_UserIsOwner_ReturnsVehicle() {
        when(vehicleJpaRepository.findByPlate("1234-ABC")).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        NewVehicleDTO result = vehicleService.getByPlate("1234-ABC", clientUser.getEmail());

        assertNotNull(result);
        assertEquals("1234-ABC", result.plate());
    }

    @Test
    void requestEntry_MechanicInWorkshop_CreatesPendingRequestAndNotifies() {
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(workshopJpaRepository.findById(workshop.getWorkshopId())).thenReturn(Optional.of(workshop));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.requestEntry(vehicle.getPlate(), workshop.getWorkshopId(), mechanicUser.getEmail());

        verify(vehicleJpaRepository).save(argThat(v -> v.getPendingWorkshop() != null));
        verify(eventPublisher).publishEvent(any(VehicleAdmissionEvent.class));
        verify(messagingTemplate).convertAndSendToUser(eq(clientUser.getEmail()), eq("/queue/notificaciones"), any(NotificationDTO.class));
    }

    @Test
    void requestEntry_WebSocketFails_ContinuesWithoutThrowing() {
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(workshopJpaRepository.findById(workshop.getWorkshopId())).thenReturn(Optional.of(workshop));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        doThrow(new RuntimeException("WebSocket Error")).when(messagingTemplate)
                .convertAndSendToUser(anyString(), anyString(), any(NotificationDTO.class));

        assertDoesNotThrow(() -> vehicleService.requestEntry(vehicle.getPlate(), workshop.getWorkshopId(), mechanicUser.getEmail()));
        verify(eventPublisher).publishEvent(any(VehicleAdmissionEvent.class));
    }

    @Test
    void approveEntry_UserIsOwner_ApprovesAndNotifies() {
        vehicle.setPendingWorkshop(workshop);

        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.approveEntry(vehicle.getPlate(), clientUser.getEmail());

        assertNull(vehicle.getPendingWorkshop());
        assertEquals(workshop, vehicle.getWorkshop());
        verify(vehicleJpaRepository).save(vehicle);
    }

    @Test
    void approveEntry_WebSocketFails_ContinuesWithoutThrowing() {
        vehicle.setPendingWorkshop(workshop);
        User managerUser = User.builder().dni("88888888M").email("manager@test.com").role(Role.MANAGER).workshop(workshop).build();

        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER)).thenReturn(Optional.of(managerUser));

        doThrow(new RuntimeException("WebSocket Error")).when(messagingTemplate)
                .convertAndSendToUser(anyString(), anyString(), any(NotificationDTO.class));

        assertDoesNotThrow(() -> vehicleService.approveEntry(vehicle.getPlate(), clientUser.getEmail()));
        assertNull(vehicle.getPendingWorkshop());
        assertEquals(workshop, vehicle.getWorkshop());
    }

    @Test
    void approveEntry_NoPendingRequest_ThrowsException() {
        vehicle.setPendingWorkshop(null);

        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        assertThrows(NoPendingRequestException.class, () ->
                vehicleService.approveEntry(vehicle.getPlate(), clientUser.getEmail())
        );
    }

    @Test
    void registerExit_VehicleInWorkshop_RemovesWorkshop() {
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.registerExit(vehicle.getPlate(), workshop.getWorkshopId(), mechanicUser.getEmail());

        assertNull(vehicle.getWorkshop());
        verify(vehicleJpaRepository).save(vehicle);
    }

    @Test
    void delete_UserIsOwner_DeletesVehicleAndBindsHistoricalDataToOrders() {
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        WorkOrder linkedOrder = new WorkOrder();
        linkedOrder.setVehicle(vehicle);
        List<WorkOrder> expectedOrders = List.of(linkedOrder);

        when(workOrderJpaRepository.findByVehicle_Plate(eq(vehicle.getPlate()), any(Pageable.class)))
                .thenReturn(new PageImpl<>(expectedOrders));

        vehicleService.delete(vehicle.getPlate(), clientUser.getEmail());

        assertEquals("1234-ABC", linkedOrder.getHistoricalPlate());
        assertEquals("Citroen C3", linkedOrder.getHistoricalBrandModel());
        assertEquals("Javier", linkedOrder.getHistoricalClientName());
        assertEquals("11111111A", linkedOrder.getHistoricalClientDni());
        assertNull(linkedOrder.getVehicle());

        verify(workOrderJpaRepository).saveAll(expectedOrders);
        verify(vehicleJpaRepository).delete(vehicle);
    }

    @Test
    void delete_UserIsNotOwner_ThrowsUnauthorized() {
        User anotherUser = User.builder()
                .dni("99999999Z")
                .email("other@test.com")
                .role(Role.CLIENT)
                .build();

        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail("other@test.com")).thenReturn(Optional.of(anotherUser));

        assertThrows(UnauthorizedActionException.class, () ->
                vehicleService.delete(vehicle.getPlate(), "other@test.com")
        );

        verify(vehicleJpaRepository, never()).delete(any());
    }

    @Test
    void changeOwner_UserIsOwner_ChangesOwnerSuccessfully() {
        User newOwner = User.builder().dni("33333333C").email("new@test.com").build();

        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByDni(newOwner.getDni())).thenReturn(Optional.of(newOwner));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.changeOwner(vehicle.getPlate(), newOwner.getDni(), clientUser.getEmail());

        assertEquals(newOwner.getDni(), vehicle.getOwner().getDni());
        verify(vehicleJpaRepository).save(vehicle);
    }

    @Test
    void addVehicle_PlateAlreadyExists_ThrowsException() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate("1234-ABC")).thenReturn(Optional.of(vehicle));

        assertThrows(VehicleAlreadyExistsException.class, () ->
                vehicleService.add(NewVehicleDTO.of(vehicle), clientUser.getEmail())
        );
    }

    @Test
    void addVehicle_WorkerWithoutWorkshop_ThrowsException() {
        User mechanicWithoutWorkshop = User.builder().dni("999").email("noworkshop@test.com").role(Role.MECHANIC).build();
        when(userJpaRepository.findByEmail(mechanicWithoutWorkshop.getEmail())).thenReturn(Optional.of(mechanicWithoutWorkshop));
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));

        assertThrows(WorkshopNotAssignedException.class, () ->
                vehicleService.add(NewVehicleDTO.of(vehicle), mechanicWithoutWorkshop.getEmail())
        );
    }

    @Test
    void getByPlate_UserIsNotOwnerAndNotInWorkshop_ThrowsUnauthorized() {
        User intruder = User.builder().dni("66666666X").email("hacker@test.com").role(Role.CLIENT).build();
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(intruder.getEmail())).thenReturn(Optional.of(intruder));

        assertThrows(UnauthorizedActionException.class, () ->
                vehicleService.getByPlate(vehicle.getPlate(), intruder.getEmail())
        );
    }

    @Test
    void edit_UserIsOwner_UpdatesVehicleSuccessfully() {
        NewVehicleDTO editDto = new NewVehicleDTO(
                "1234-ABC", "Ford", "Mustang", null, 5000L,
                "Gasolina", 400, 500, "Pirelli",
                new ArrayList<>(), null, null, null, null, null, null
        );

        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.edit(editDto, vehicle.getPlate(), clientUser.getEmail());

        assertEquals("Ford", vehicle.getBrand());
        assertEquals("Mustang", vehicle.getModel());
        verify(vehicleJpaRepository).save(vehicle);
    }

    @Test
    void edit_PlateChangedAndAlreadyExists_ThrowsException() {
        NewVehicleDTO editDto = new NewVehicleDTO(
                "9999-NEW", "Ford", "Mustang", null, 5000L,
                "Gasolina", 400, 500, "Pirelli",
                null, null, null, null, null, null, null
        );

        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(vehicleJpaRepository.findByPlate("9999-NEW")).thenReturn(Optional.of(new Vehicle()));

        assertThrows(VehicleAlreadyExistsException.class, () ->
                vehicleService.edit(editDto, vehicle.getPlate(), clientUser.getEmail())
        );
    }

    @Test
    void rejectEntry_UserIsOwner_RejectsSuccessfully() {
        vehicle.setPendingWorkshop(workshop);
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        vehicleService.rejectEntry(vehicle.getPlate(), clientUser.getEmail());

        assertNull(vehicle.getPendingWorkshop());
        verify(vehicleJpaRepository).save(vehicle);
    }

    @Test
    void getVehicleHistory_AuthorizedUser_ReturnsHistory() {
        when(vehicleJpaRepository.findByPlate(vehicle.getPlate())).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(workOrderJpaRepository.findByVehicle_Plate(vehicle.getPlate(), pageable))
                .thenReturn(new PageImpl<>(List.of()));

        Page<NewWorkOrderResponseDTO> result = vehicleService.getVehicleHistory(vehicle.getPlate(), clientUser.getEmail(), pageable);

        assertNotNull(result);
        verify(workOrderJpaRepository).findByVehicle_Plate(vehicle.getPlate(), pageable);
    }

    @Test
    void searchVehicles_InvalidType_ThrowsException() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        assertThrows(InvalidSearchTypeException.class, () ->
                vehicleService.searchVehicles("ford", null, "TIPO_FALSO", clientUser.getEmail(), pageable)
        );
    }

    @Test
    void searchVehicles_EmptyTextTypeOwner_DelegatesToGetByOwner() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(vehicleJpaRepository.findByOwner_Dni(clientUser.getDni(), pageable)).thenReturn(new PageImpl<>(List.of(vehicle)));

        Page<NewVehicleDTO> result = vehicleService.searchVehicles("   ", null, "OWNER", clientUser.getEmail(), pageable);

        assertFalse(result.isEmpty());
        verify(vehicleJpaRepository).findByOwner_Dni(clientUser.getDni(), pageable);
    }

    @Test
    void getByPlate_MechanicWithPendingWorkshop_ReturnsVehicle() {
        vehicle.setWorkshop(null);
        vehicle.setPendingWorkshop(workshop);

        when(vehicleJpaRepository.findByPlate("1234-ABC")).thenReturn(Optional.of(vehicle));
        when(userJpaRepository.findByEmail(mechanicUser.getEmail())).thenReturn(Optional.of(mechanicUser));

        NewVehicleDTO result = vehicleService.getByPlate("1234-ABC", mechanicUser.getEmail());

        assertNotNull(result);
    }
}