package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.*;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkshopServiceTest {

    @Mock
    private WorkshopJpaRepository workshopJpaRepository;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private Uploader uploader;

    @InjectMocks
    private WorkshopService workshopService;

    private Workshop workshop;
    private User manager;
    private User mechanic;
    private NewWorkshopDTO workshopDTO;

    @BeforeEach
    void setUp() {
        workshop = new Workshop();
        workshop.setWorkshopId(1L);
        workshop.setWorkshopName("Taller Paco");
        workshop.setAddress("Calle Falsa 123");
        workshop.setWorkshopPhone("600123456");
        workshop.setWorkshopEmail("contacto@tallerpaco.com");
        workshop.setIcon("http://res.cloudinary.com/demo/image/upload/v1234/carlog/workshops/icono1.jpg");

        manager = new User();
        manager.setDni("11111111A");
        manager.setEmail("manager@tallerpaco.com");
        manager.setRole(Role.MANAGER);
        manager.setWorkshop(workshop);

        mechanic = new User();
        mechanic.setDni("22222222B");
        mechanic.setEmail("mecanico@tallerpaco.com");
        mechanic.setRole(Role.MECHANIC);
        mechanic.setWorkshop(workshop);

        workshopDTO = new NewWorkshopDTO(
                null,
                "Taller Paco",
                "Calle Falsa 123",
                "600123456",
                "contacto@tallerpaco.com",
                null
        );
    }

    @Test
    void getAll_Success_ReturnsWorkshopList() {
        when(workshopJpaRepository.findAll()).thenReturn(List.of(workshop));

        List<NewWorkshopDTO> result = workshopService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Taller Paco", result.get(0).workshopName());
        verify(workshopJpaRepository).findAll();
    }

    @Test
    void getWorkshopById_Success_WhenUserBelongsToWorkshop() {
        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        NewWorkshopDTO result = workshopService.getWorkshopById(1L, mechanic.getEmail());

        assertNotNull(result);
        assertEquals("Taller Paco", result.workshopName());
    }

    @Test
    void getWorkshopById_ThrowsUnauthorized_WhenUserFromDifferentWorkshop() {
        Workshop anotherWorkshop = new Workshop();
        anotherWorkshop.setWorkshopId(2L);
        mechanic.setWorkshop(anotherWorkshop);

        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(UnauthorizedActionException.class, () ->
                workshopService.getWorkshopById(1L, mechanic.getEmail())
        );
    }

    @Test
    void getWorkshopById_ThrowsWorkshopNotFound() {
        when(workshopJpaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(WorkshopNotFoundException.class, () ->
                workshopService.getWorkshopById(99L, mechanic.getEmail())
        );
    }

    @Test
    void add_Success_WithoutIcon() {
        User newOwner = new User();
        newOwner.setEmail("nuevo@test.com");

        when(workshopJpaRepository.findByWorkshopName(workshopDTO.workshopName())).thenReturn(Optional.empty());
        when(userJpaRepository.findByEmail(newOwner.getEmail())).thenReturn(Optional.of(newOwner));
        when(workshopJpaRepository.save(any(Workshop.class))).thenReturn(workshop);

        NewWorkshopDTO result = workshopService.add(workshopDTO, newOwner.getEmail());

        assertNotNull(result);
        assertEquals(workshop.getWorkshopId(), newOwner.getWorkshop().getWorkshopId());
        verify(userJpaRepository).save(newOwner);
        verify(cloudinary, never()).uploader();
    }

    @Test
    void add_Success_WithBase64Icon() throws Exception {
        NewWorkshopDTO dtoWithBase64 = new NewWorkshopDTO(
                null, "Taller Paco", "Calle Falsa 123", "600123456", "contacto@tallerpaco.com", "data:image/png;base64,iVBOR..."
        );
        User newOwner = new User();
        newOwner.setEmail("nuevo@test.com");

        when(workshopJpaRepository.findByWorkshopName(dtoWithBase64.workshopName())).thenReturn(Optional.empty());
        when(userJpaRepository.findByEmail(newOwner.getEmail())).thenReturn(Optional.of(newOwner));
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(anyString(), anyMap())).thenReturn(Map.of("secure_url", "http://cloud.com/icon.png"));
        when(workshopJpaRepository.save(any(Workshop.class))).thenReturn(workshop);

        NewWorkshopDTO result = workshopService.add(dtoWithBase64, newOwner.getEmail());

        assertNotNull(result);
        verify(uploader).upload(eq(dtoWithBase64.icon()), anyMap());
    }

    @Test
    void add_ThrowsWorkshopAlreadyExists() {
        when(workshopJpaRepository.findByWorkshopName(workshopDTO.workshopName())).thenReturn(Optional.of(workshop));

        assertThrows(WorkshopAlreadyExistsException.class, () ->
                workshopService.add(workshopDTO, manager.getEmail())
        );
    }

    @Test
    void add_ThrowsUserAlreadyHasWorkshop() {
        when(workshopJpaRepository.findByWorkshopName(workshopDTO.workshopName())).thenReturn(Optional.empty());
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));

        assertThrows(UserAlreadyHasWorkshopException.class, () ->
                workshopService.add(workshopDTO, manager.getEmail())
        );
    }

    @Test
    void edit_Success_UpdatesDataAndClearsIcon() throws Exception {
        NewWorkshopDTO editDto = new NewWorkshopDTO(1L, "Taller Modificado", "Nueva dir", "123", "new@test.com", "");

        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(cloudinary.uploader()).thenReturn(uploader);
        when(workshopJpaRepository.save(any(Workshop.class))).thenReturn(workshop);

        workshopService.edit(editDto, 1L, null, manager.getEmail());

        assertNull(workshop.getIcon());
        assertEquals("Taller Modificado", workshop.getWorkshopName());
        verify(uploader).destroy(anyString(), anyMap());
    }

    @Test
    void edit_Success_UpdatesDataAndUploadsMultipartFile() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "icon.png", "image/png", "img_data".getBytes());
        NewWorkshopDTO editDto = new NewWorkshopDTO(1L, "Taller Modificado", "Nueva dir", "123", "new@test.com", "dummy_icon");

        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(Map.of("secure_url", "http://cloud.com/new.png"));
        when(workshopJpaRepository.save(any(Workshop.class))).thenReturn(workshop);

        workshopService.edit(editDto, 1L, file, manager.getEmail());

        assertEquals("http://cloud.com/new.png", workshop.getIcon());
        verify(uploader).destroy(anyString(), anyMap());
        verify(uploader).upload(any(byte[].class), anyMap());
    }

    @Test
    void edit_ThrowsUnauthorized_WhenUserIsMechanic() {
        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(UnauthorizedActionException.class, () ->
                workshopService.edit(workshopDTO, 1L, null, mechanic.getEmail())
        );
    }

    @Test
    void edit_ThrowsWorkshopAlreadyExists_WhenNameIsTakenByAnotherWorkshop() {
        NewWorkshopDTO editDto = new NewWorkshopDTO(1L, "Otro Taller", "dir", "1", "e@e.com", null);
        Workshop anotherWorkshop = new Workshop();
        anotherWorkshop.setWorkshopId(2L);

        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(workshopJpaRepository.findByWorkshopName("Otro Taller")).thenReturn(Optional.of(anotherWorkshop));

        assertThrows(WorkshopAlreadyExistsException.class, () ->
                workshopService.edit(editDto, 1L, null, manager.getEmail())
        );
    }

    @Test
    void delete_Success_DeletesWorkshopAndIcon() throws Exception {
        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(cloudinary.uploader()).thenReturn(uploader);

        workshopService.delete(1L, manager.getEmail());

        verify(workshopJpaRepository).delete(workshop);
        verify(uploader).destroy(anyString(), anyMap());
    }

    @Test
    void delete_ThrowsUnauthorized_WhenUserIsMechanic() {
        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(mechanic.getEmail())).thenReturn(Optional.of(mechanic));

        assertThrows(UnauthorizedActionException.class, () ->
                workshopService.delete(1L, mechanic.getEmail())
        );

        verify(workshopJpaRepository, never()).delete(any());
    }

    @Test
    void uploadMultipartFileToCloudinary_CatchesException_ReturnsNull() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "icon.png", "image/png", "img_data".getBytes());

        when(workshopJpaRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(userJpaRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(cloudinary.uploader()).thenReturn(uploader);

        when(uploader.upload(any(byte[].class), anyMap())).thenThrow(new RuntimeException("Cloudinary Error"));
        when(workshopJpaRepository.save(any(Workshop.class))).thenReturn(workshop);

        workshopService.edit(workshopDTO, 1L, file, manager.getEmail());

        assertNull(workshop.getIcon());
    }
}