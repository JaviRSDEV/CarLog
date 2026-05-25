package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkshopService {

    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private static final String ERROR_MSG = "Taller no encontrado";

    private final Cloudinary cloudinary;

    public List<NewWorkshopDTO> getAll(){
        var result = workshopJpaRepository.findAll();
        return result.stream().map(NewWorkshopDTO::of).toList();
    }

    public NewWorkshopDTO getWorkshopById(Long id, String email){
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(ERROR_MSG));

        verifyWorkshopReadAccess(workshop, email);
        return NewWorkshopDTO.of(workshop);
    }

    @Transactional
    public NewWorkshopDTO add(NewWorkshopDTO dto, String email) {
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if (result.isPresent()) {
            throw new WorkshopAlreadyExistsException("Ya existe un taller con ese nombre: " + dto.workshopName());
        }

        User creator = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        String targetOwnerEmail = (creator.getRole() == Role.ADMIN && dto.ownerEmail() != null && !dto.ownerEmail().isEmpty())
                ? dto.ownerEmail()
                : email;

        User workshopOwner = userJpaRepository.findByEmail(targetOwnerEmail)
                .orElseThrow(() -> new UserNotFoundException("No se encontró el usuario con email: " + targetOwnerEmail));

        if (workshopOwner.getWorkshop() != null) {
            throw new UserAlreadyHasWorkshopException("Este usuario ya pertenece o es administrador de otro taller");
        }

        String iconUrl = dto.icon();
        if (iconUrl != null && iconUrl.startsWith("data:image")) {
            iconUrl = uploadBase64ToCloudinary(iconUrl);
        }

        var newWorkshop = Workshop.builder()
                .workshopName(dto.workshopName())
                .address(dto.address())
                .workshopPhone(dto.workshopPhone())
                .workshopEmail(dto.workshopEmail())
                .icon(iconUrl)
                .build();

        newWorkshop = workshopJpaRepository.save(newWorkshop);

        if (workshopOwner.getRole() != Role.ADMIN) {
            workshopOwner.setRole(Role.MANAGER);
        }
        workshopOwner.setWorkshop(newWorkshop);
        userJpaRepository.save(workshopOwner);

        return NewWorkshopDTO.of(newWorkshop);
    }

    @Transactional
    public NewWorkshopDTO edit(NewWorkshopDTO dto, Long id, MultipartFile file, String email) {
        return workshopJpaRepository.findById(id).map(workshop -> {
            verifyWorkshopManagerAccess(workshop, email);

            User currentUser = userJpaRepository.findByEmail(email).orElseThrow();

            if (dto.workshopName() != null && !workshop.getWorkshopName().equalsIgnoreCase(dto.workshopName()) && workshopJpaRepository.findByWorkshopName(dto.workshopName()).isPresent()) {
                throw new WorkshopAlreadyExistsException("Error: ya existe otro taller registrado con ese nombre");
            }

            if(currentUser.getRole() == Role.ADMIN && dto.ownerEmail() != null && !dto.ownerEmail().isEmpty()){
                Optional<User> currentManagerOpt = workshop.getEmployees().stream()
                        .findFirst();

                if(currentManagerOpt.isEmpty() || !currentManagerOpt.get().getEmail().equalsIgnoreCase(dto.ownerEmail())){
                    User newOwner = userJpaRepository.findByEmail(dto.ownerEmail())
                            .orElseThrow(() -> new UserNotFoundException("El nuevo dueño especificado no existe: " + dto.ownerEmail()));

                    if(newOwner.getWorkshop() != null && !newOwner.getWorkshop().getWorkshopId().equals(id)){
                        throw new UserAlreadyHasWorkshopException("El nuevo usuario ya pertenece a otro taller");
                    }

                    if(currentManagerOpt.isPresent()){
                        User oldManager = currentManagerOpt.get();
                        oldManager.setRole(Role.CLIENT);
                        oldManager.setWorkshop(null);
                        userJpaRepository.save(oldManager);
                    }

                    newOwner.setRole(Role.MANAGER);
                    newOwner.setWorkshop(workshop);
                    userJpaRepository.save(newOwner);
                }
            }

            if (file != null && !file.isEmpty()) {
                deleteFromCloudinary(workshop.getIcon());
                workshop.setIcon(uploadMultipartFileToCloudinary(file));
            }
            else if (dto.icon() == null || dto.icon().isEmpty()) {
                deleteFromCloudinary(workshop.getIcon());
                workshop.setIcon(null);
            }

            workshop.setWorkshopName(dto.workshopName());
            workshop.setAddress(dto.address());
            workshop.setWorkshopPhone(dto.workshopPhone());
            workshop.setWorkshopEmail(dto.workshopEmail());

            return NewWorkshopDTO.of(workshopJpaRepository.save(workshop));
        }).orElseThrow(() -> new WorkshopNotFoundException(ERROR_MSG));
    }

    @Transactional
    public NewWorkshopDTO delete(Long id, String email) {
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(ERROR_MSG));

        verifyWorkshopManagerAccess(workshop, email);
        String iconUrl = workshop.getIcon();

        List<WorkOrder> workOrders = workOrderJpaRepository.findByWorkshop_workshopId(id);
        for(WorkOrder order : workOrders){
            order.setHistoricalWorkshopName(workshop.getWorkshopName());
            order.setWorkshop(null);
            workOrderJpaRepository.save(order);
        }

        if(workshop.getVehicles() != null){
            for(Vehicle vehicle : new ArrayList<>(workshop.getVehicles())){
                vehicle.setWorkshop(null);

                if(vehicle.getPendingWorkshop() != null && vehicle.getPendingWorkshop().getWorkshopId().equals(id)){
                    vehicle.setPendingWorkshop(null);
                }
            }
            workshop.getVehicles().clear();
        }

        if (workshop.getEmployees() != null && !workshop.getEmployees().isEmpty()) {
            for (User employee : new java.util.ArrayList<>(workshop.getEmployees())) {
                employee.setWorkshop(null);

                if (employee.getRole() != Role.ADMIN) {
                    employee.setRole(Role.CLIENT);
                }
                userJpaRepository.save(employee);
            }
            workshop.getEmployees().clear();
        }

        workshopJpaRepository.delete(workshop);

        if (iconUrl != null) {
            deleteFromCloudinary(iconUrl);
        }

        return NewWorkshopDTO.of(workshop);
    }

    private String uploadMultipartFileToCloudinary(MultipartFile file) {
        try {
            log.info("Subiendo icono del taller (Multipart) a Cloudinary...");
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", "carlog/workshops",
                    "resource_type", "image"
            ));
            String secureUrl = (String) uploadResult.get("secure_url");
            log.info("Icono subido con éxito: {}", secureUrl);
            return secureUrl;
        } catch (Exception e) {
            log.error("Error al subir archivo a Cloudinary: {}", e.getMessage());
            return null;
        }
    }

    private String uploadBase64ToCloudinary(String base64Image) {
        try {
            log.info("Subiendo icono del taller (Base64) a Cloudinary...");
            var uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.asMap(
                    "folder", "carlog/workshops",
                    "resource_type", "image"
            ));

            String secureUrl = (String) uploadResult.get("secure_url");
            log.info("Icono subido con éxito: {}", secureUrl);
            return secureUrl;
        } catch (Exception e) {
            log.error("Error al subir icono Base64 a Cloudinary: {}", e.getMessage());
            return null;
        }
    }

    private void deleteFromCloudinary(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("cloudinary")) {
            return;
        }

        try {
            String publicId = "carlog/workshops/" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Icono eliminado de Cloudinary: {}", publicId);
        } catch (Exception e) {
            log.error("Error al eliminar icono físico en la nube: {}", e.getMessage());
        }
    }

    private void verifyWorkshopManagerAccess(Workshop workshop, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        boolean isManagerOrCoManager = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER;

        if (!isManagerOrCoManager) {
            throw new UnauthorizedActionException("Acceso denegado: Solo administradores pueden realizar esta acción.");
        }

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshop.getWorkshopId())) {
            throw new UnauthorizedActionException("Acceso denegado: No eres el responsable de este taller.");
        }
    }

    private void verifyWorkshopReadAccess(Workshop workshop, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null ||
                !currentUser.getWorkshop().getWorkshopId().equals(workshop.getWorkshopId())) {
            throw new UnauthorizedActionException("Acceso denegado: No perteneces a este taller.");
        }
    }
}