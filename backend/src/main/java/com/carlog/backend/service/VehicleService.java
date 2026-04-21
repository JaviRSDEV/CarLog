package com.carlog.backend.service;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.error.*;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final Cloudinary cloudinary;

    public Page<NewVehicleDTO> getMyVehicles(String email, Pageable pageable) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isWorker = currentUser.getRole().isWorker();

        if (isWorker && currentUser.getWorkshop() != null) {
            return vehicleJpaRepository.findByWorkshop_WorkshopId(currentUser.getWorkshop().getWorkshopId(), pageable)
                    .map(NewVehicleDTO::of);
        }

        return vehicleJpaRepository.findByOwner_Dni(currentUser.getDni(), pageable)
                .map(NewVehicleDTO::of);
    }

    public Page<NewVehicleDTO> getByWorkshop(Long workshopId, String email, Pageable pageable){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshopId)) {
            throw new UnauthorizedActionException("Acceso denegado: No perteneces a este taller.");
        }

        return vehicleJpaRepository.findByWorkshop_WorkshopId(workshopId, pageable)
                .map(NewVehicleDTO::of);
    }

    public NewVehicleDTO getByPlate(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        verifyVehicleReadAccess(vehicle, email);
        return NewVehicleDTO.of(vehicle);
    }

    public Page<NewVehicleDTO> getByOwner(String ownerDni, String email, Pageable pageable){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getDni().equals(ownerDni)) {
            throw new UnauthorizedActionException("Acceso denegado: No puedes ver los vehículos de otro usuario.");
        }

        return vehicleJpaRepository.findByOwner_Dni(ownerDni, pageable)
                .map(NewVehicleDTO::of);
    }

    public Page<NewVehicleDTO> getVehiclesAssignedToMechanic(String mechanicDni, String email, Pageable pageable){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getDni().equals(mechanicDni) && currentUser.getRole() != Role.MANAGER) {
            throw new UnauthorizedActionException("Acceso denegado: No puedes ver los vehículos de otro mecánico.");
        }

        return vehicleJpaRepository.findDistinctVehiclesByMechanicDni(mechanicDni, pageable)
                .map(NewVehicleDTO::of);
    }

    public NewVehicleDTO add(NewVehicleDTO dto, String userEmail){
        User connectedUser = userJpaRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        User owner;
        Workshop currentWorkshop;

        List<String> finalImageRoutes = new ArrayList<>();

        if(dto.images() != null && !dto.images().isEmpty()){
            for(String img : dto.images()){
                if(img != null && img.startsWith("data:image")){
                    String savedRoute = uploadToCloudinary(img);
                    if(savedRoute != null) finalImageRoutes.add(savedRoute);
                }
            }
        }

        if(connectedUser.getRole().isWorker()){
            if(dto.ownerId() != null && !dto.ownerId().isBlank()){
                owner = userJpaRepository.findByDni(dto.ownerId()).orElseThrow(() -> new UserNotFoundException(dto.ownerId()));
            }else{
                owner = connectedUser;
            }

            currentWorkshop = connectedUser.getWorkshop();
            if(currentWorkshop == null && connectedUser.getRole() != Role.DIY){
                throw new WorkshopNotAssignedException("Error: El mecánico o manager no dispone de taller asignado.");
            }
        }else{
            owner = connectedUser;
            currentWorkshop = null;
        }

        if(vehicleJpaRepository.findByPlate(dto.plate()).isPresent())
            throw new VehicleAlreadyExistsException("Ya existe un vehículo con la matrícula " + dto.plate());

        var newVehicle = Vehicle.builder()
                .plate(dto.plate())
                .brand(dto.brand())
                .model(dto.model())
                .kilometers(dto.kilometers())
                .engine(dto.engine())
                .horsePower(dto.horsePower())
                .torque(dto.torque())
                .tires(dto.tires())
                .images(finalImageRoutes)
                .lastMaintenance(dto.lastMaintenance())
                .owner(owner)
                .workshop(currentWorkshop)
                .build();

        return NewVehicleDTO.of(vehicleJpaRepository.save(newVehicle));
    }

    private String uploadToCloudinary(String base64Image) {
        try {
            log.info("Subiendo nueva imagen a Cloudinary...");
            var uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.asMap(
                    "folder", "carlog/vehicles",
                    "resource_type", "image"
            ));

            String secureUrl = (String) uploadResult.get("secure_url");
            log.info("Imagen subida con éxito: {}", secureUrl);
            return secureUrl;
        } catch (Exception e) {
            log.error("Error al subir imagen a Cloudinary: {}", e.getMessage());
            return null;
        }
    }

    private void deleteFromCloudinary(String imageUrl){
        if(imageUrl == null || !imageUrl.contains("cloudinary")) return;

        try {
            String publicId = "carlog/vehicles/" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Imagen eliminada de Cloudinary: {}", publicId);
        } catch (Exception e) {
            log.error("No se pudo eliminar la imagen de la nube: {}", e.getMessage());
        }
    }

    public NewVehicleDTO edit(NewVehicleDTO dto, String plate, String email){
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return vehicleJpaRepository.findByPlate(plate).map(vehicle -> {

            if(vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())){
                throw new UnauthorizedActionException("Acceso denegado: No tienes permiso para editar el vehículo");
            }

            if(!dto.plate().equals(vehicle.getPlate()) && vehicleJpaRepository.findByPlate(dto.plate()).isPresent()){
                throw new VehicleAlreadyExistsException("La matrícula " + dto.plate() + " ya está en uso");
            }

            List<String> oldImages = vehicle.getImages() != null ? new ArrayList<>(vehicle.getImages()) : new ArrayList<>();
            List<String> updatedImagesRoutes = new ArrayList<>();

            if(dto.images() != null && !dto.images().isEmpty()) {
                for(String img : dto.images()){
                    if(img.startsWith("http://") || img.startsWith("https://")){
                        updatedImagesRoutes.add(img);
                    }
                    else if(img.startsWith("data:image")){
                        String savedRoute = uploadToCloudinary(img);
                        if(savedRoute != null) updatedImagesRoutes.add(savedRoute);
                    }
                }
            }

            for(String oldImageUrl : oldImages){
                if(!updatedImagesRoutes.contains(oldImageUrl)){
                    deleteFromCloudinary(oldImageUrl);
                }
            }

            vehicle.setPlate(dto.plate());
            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setKilometers(dto.kilometers());
            vehicle.setEngine(dto.engine());
            vehicle.setHorsePower(dto.horsePower());
            vehicle.setTorque(dto.torque());
            vehicle.setTires(dto.tires());
            vehicle.setImages(updatedImagesRoutes);
            vehicle.setLastMaintenance(dto.lastMaintenance());

            if(dto.ownerId() != null){
                User u = userJpaRepository.findByDni(dto.ownerId()).orElseThrow(() -> new UserNotFoundException(dto.ownerId()));
                vehicle.setOwner(u);
            }

            return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
        }).orElseThrow(() -> new VehicleNotFoundException(plate));
    }

    public NewVehicleDTO requestEntry(String plate, Long workshopId, String email) {
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshopId)) {
            throw new UnauthorizedActionException("Acceso denegado: No perteneces a este taller.");
        }

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        Workshop workshop = workshopJpaRepository.findById(workshopId).orElseThrow(() -> new WorkshopNotFoundException("Taller no encontrado"));

        vehicle.setPendingWorkshop(workshop);
        Vehicle savedVehicle = vehicleJpaRepository.save(vehicle);

        String ownerDni = vehicle.getOwner().getDni();

        NotificationDTO notif = NotificationDTO.builder()
                .type("VEHICLE_REQUEST")
                .title("Solicitud de Ingreso")
                .message("El taller " + workshop.getWorkshopName() + " solicita el ingreso de tu vehículo " + plate)
                .build();

        messagingTemplate.convertAndSend("/topic/notificaciones/" + ownerDni, notif);

        return NewVehicleDTO.of(savedVehicle);
    }

    public NewVehicleDTO approveEntry(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!vehicle.getOwner().getDni().equals(currentUser.getDni())) {
            throw new UnauthorizedActionException("No tienes permiso para aprobar el ingreso de este vehículo");
        }

        if(vehicle.getPendingWorkshop() == null){
            throw new NoPendingRequestException("Este vehículo no tiene ninguna solicitud pendiente");
        }

        Workshop workshop = vehicle.getPendingWorkshop();

        vehicle.setWorkshop(vehicle.getPendingWorkshop());
        vehicle.setPendingWorkshop(null);

        Vehicle savedVehicle = vehicleJpaRepository.save(vehicle);

        try{
            User manager = userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER).orElse(null);

            if(manager != null){
                NotificationDTO alert = NotificationDTO.builder()
                        .type("NEW_FLEET_VEHICLE")
                        .title("¡Nuevo vehiculo ingresado!")
                        .message("El cliente ha autorizado el ingreso de la matricula " + plate.toUpperCase())
                        .extraData(plate)
                        .build();

                messagingTemplate.convertAndSend("/topic/notificaciones/" + manager.getDni(), alert);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return NewVehicleDTO.of(savedVehicle);
    }

    public NewVehicleDTO rejectEntry(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!vehicle.getOwner().getDni().equals(currentUser.getDni())){
            throw new UnauthorizedActionException("No tienes permiso para rechazar el ingreso de este vehículo");
        }

        vehicle.setPendingWorkshop(null);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO registerExit(String plate, Long workshopId, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshopId)) {
            throw new UnauthorizedActionException("Acceso denegado: No puedes registrar salidas de otro taller.");
        }

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        if(vehicle.getWorkshop() == null || !vehicle.getWorkshop().getWorkshopId().equals(workshopId)){
            throw new VehicleNotInWorkshopException("No puedes dar salida a un coche que no está registrado en el taller");
        }
        vehicle.setWorkshop(null);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO changeOwner(String plate, String newOwnerId, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        if(vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())){
            throw new UnauthorizedActionException("Acceso denegado: Solo el dueño actual puede transferir el vehículo.");
        }

        User newOwner = userJpaRepository.findByDni(newOwnerId).orElseThrow(() -> new UserNotFoundException(newOwnerId));
        vehicle.setOwner(newOwner);

        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    @Transactional
    public NewVehicleDTO delete(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())){
            throw new UnauthorizedActionException("Acceso denegado: No tienes permiso para eliminar el vehículo");
        }
        NewVehicleDTO deletedVehicle = NewVehicleDTO.of(vehicle);

        if(vehicle.getImages() != null){
            for(String imageUrl : vehicle.getImages()){
                deleteFromCloudinary(imageUrl);
            }
        }

        vehicleJpaRepository.delete(vehicle);
        return deletedVehicle;
    }

    public Page<NewWorkOrderResponseDTO> getVehicleHistory(String plate, String email, Pageable pageable){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        verifyVehicleReadAccess(vehicle, email);

        return workOrderJpaRepository.findByVehicle_Plate(plate, pageable)
                .map(NewWorkOrderResponseDTO::of);
    }

    private void verifyVehicleReadAccess(Vehicle vehicle, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (vehicle.getOwner() != null && vehicle.getOwner().getDni().equals(currentUser.getDni())) {
            return;
        }

        if (currentUser.getRole().isWorker()) {
            boolean inWorkshop = vehicle.getWorkshop() != null && currentUser.getWorkshop() != null &&
                    vehicle.getWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId());

            boolean pendingWorkshop = vehicle.getPendingWorkshop() != null && currentUser.getWorkshop() != null &&
                    vehicle.getPendingWorkshop().getWorkshopId().equals(currentUser.getWorkshop().getWorkshopId());

            if (!inWorkshop && !pendingWorkshop) {
                throw new UnauthorizedActionException("Acceso denegado: El vehículo no se encuentra en tu taller.");
            }
        } else {
            throw new UnauthorizedActionException("Acceso denegado: Este vehículo no es tuyo.");
        }
    }

    public Page<NewVehicleDTO> searchVehicles(String searchText, Long workshopId, String type, String email, Pageable pageable) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (searchText == null || searchText.trim().isEmpty()) {
            if ("OWNER".equalsIgnoreCase(type)) return getByOwner(currentUser.getDni(), email, pageable);
            if ("ASSIGNED".equalsIgnoreCase(type)) return getVehiclesAssignedToMechanic(currentUser.getDni(), email, pageable);
            if ("WORKSHOP".equalsIgnoreCase(type)) return getByWorkshop(workshopId, email, pageable);
        }

        String text = searchText.toLowerCase();

        if ("OWNER".equalsIgnoreCase(type)) {
            return vehicleJpaRepository.searchByOwnerAndText(currentUser.getDni(), text, pageable)
                    .map(NewVehicleDTO::of);

        } else if ("ASSIGNED".equalsIgnoreCase(type)) {
            return vehicleJpaRepository.searchDistinctVehiclesByMechanicDniAndText(currentUser.getDni(), text, pageable)
                    .map(NewVehicleDTO::of);

        } else if ("WORKSHOP".equalsIgnoreCase(type)) {
            if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshopId)) {
                throw new UnauthorizedActionException("Acceso denegado: No perteneces a este taller.");
            }
            return vehicleJpaRepository.searchByWorkshopAndText(workshopId, text, pageable)
                    .map(NewVehicleDTO::of);
        }

        throw new InvalidSearchTypeException("Tipo de búsqueda no válido");
    }
}