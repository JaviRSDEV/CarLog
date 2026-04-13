package com.carlog.backend.service;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.VehicleNotFoundException;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<NewVehicleDTO> getAll(){
        var result = vehicleJpaRepository.findAll();
        return result.stream().map(NewVehicleDTO::of).toList();
    }

    public List<NewVehicleDTO> getByWorkshop(Long workshopId, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || currentUser.getWorkshop().getWorkshopId() != (workshopId)) {
            throw new RuntimeException("Acceso denegado: No perteneces a este taller.");
        }

        var result = vehicleJpaRepository.findByWorkshop_WorkshopId(workshopId);
        return result.stream().map(NewVehicleDTO::of).toList();
    }

    public NewVehicleDTO getByPlate(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        verifyVehicleReadAccess(vehicle, email);

        return NewVehicleDTO.of(vehicle);
    }

    public List<NewVehicleDTO> getByOwner(String ownerDni, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getDni().equals(ownerDni)) {
            throw new RuntimeException("Acceso denegado: No puedes ver los vehículos de otro usuario.");
        }

        var result = vehicleJpaRepository.findByOwner_Dni(ownerDni);
        return result.stream().map(NewVehicleDTO::of).toList();
    }

    public List<NewVehicleDTO> getVehiclesAssignedToMechanic(String mechanicDni, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getDni().equals(mechanicDni) && currentUser.getRole() != Role.MANAGER) {
            throw new RuntimeException("Acceso denegado: No puedes ver los vehículos de otro mecánico.");
        }

        List<WorkOrder> workOrders = workOrderJpaRepository.findByMechanic_Dni(mechanicDni);
        return workOrders.stream()
                .map(WorkOrder::getVehicle)
                .distinct()
                .map(NewVehicleDTO::of)
                .toList();
    }

    public NewVehicleDTO add(NewVehicleDTO dto, String userDni){
        User connectedUser = userJpaRepository.findByDni(userDni).orElseThrow(() -> new UserNotFoundException(userDni));
        User owner;
        Workshop currentWorkshop;

        List<String> finalImageRoutes = new ArrayList<>();

        if(dto.images() != null && !dto.images().isEmpty()){
            for(int i = 0; i < dto.images().size(); i++){
                String img = dto.images().get(i);
                if(img != null && img.startsWith("data:image")){
                    String savedRoute = saveImageOnDisk(img, dto.plate() + "_" + i);
                    if(savedRoute != null) finalImageRoutes.add(savedRoute);
                }
            }
        }

        boolean isWorker = connectedUser.getRole() == Role.MECHANIC || connectedUser.getRole() == Role.MANAGER || connectedUser.getRole() == Role.CO_MANAGER || connectedUser.getRole() == Role.DIY;
        if(isWorker){
            if(dto.ownerId() != null && !dto.ownerId().isBlank()){
                owner = userJpaRepository.findByDni(dto.ownerId()).orElseThrow(() -> new UserNotFoundException(dto.ownerId()));
            }else{
                owner = connectedUser;
            }

            currentWorkshop = connectedUser.getWorkshop();
            if(currentWorkshop == null && connectedUser.getRole() != Role.DIY){
                throw new RuntimeException("Error: El mecanico o manager no dispone de taller asignado.");
            }
        }else{
            owner = connectedUser;
            currentWorkshop = null;
        }

        if(vehicleJpaRepository.findByPlate(dto.plate()).isPresent())
            throw new RuntimeException("Ya existe un vehiculo con la matricula " + dto.plate());

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

    private String saveImageOnDisk(String base64Image, String plate){
        try{
            String extension = ".jpeg";
            if(base64Image.contains("data:image/png")) extension = ".png";
            else if (base64Image.contains("data:image/webp")) extension = ".webp";
            else if (base64Image.contains("data:image/gif")) extension = ".gif";

            String[] parts = base64Image.split(",");
            String originalImage = parts.length > 1 ? parts[1] : parts[0];
            byte[] imageBytes = Base64.getDecoder().decode(originalImage);

            Path directory = Paths.get("uploads");
            if(!Files.exists(directory)){
                Files.createDirectories(directory);
            }

            String fileName = plate + extension;
            Path absolutePath = directory.resolve(fileName);

            Files.write(absolutePath, imageBytes);

            return "http://localhost:8081/uploads/" + fileName;
        }catch (IOException e){
            System.err.println("Error al guardar la imagen: " + e.getMessage());
            return null;
        }
    }

    private void deleteImageFromDisk(String imageUrl){
        if(imageUrl == null || !imageUrl.contains("/uploads/")) return;

        try{
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get("uploads").resolve(fileName);

            Files.deleteIfExists(filePath);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public NewVehicleDTO edit(NewVehicleDTO dto, String plate, String email){
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return vehicleJpaRepository.findByPlate(plate).map(vehicle -> {

            if(vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())){
                throw new RuntimeException("Acceso denegado: No tienes permiso para editar el vehículo");
            }

            if(!dto.plate().equals(vehicle.getPlate()) && vehicleJpaRepository.findByPlate(dto.plate()).isPresent()){
                throw new RuntimeException("La matrícula " + dto.plate() + " ya está en uso");
            }

            List<String> oldImages = vehicle.getImages() != null ? new ArrayList<>(vehicle.getImages()) : new ArrayList<>();
            List<String> updatedImagesRoutes = new ArrayList<>();
            if(dto.images() != null && !dto.images().isEmpty()) {
                for(int i = 0; i < dto.images().size(); i++){
                    String img = dto.images().get(i);

                    if(img.startsWith("http://") || img.startsWith("https://")){
                        updatedImagesRoutes.add(img);
                    }
                    else if(img.startsWith("data:image")){
                        String savedRoute = saveImageOnDisk(img, dto.plate() + "_" + UUID.randomUUID().toString().substring(0,8));
                        if(savedRoute != null) updatedImagesRoutes.add(savedRoute);
                    }
                }
            }

            for(String oldImageUrl : oldImages){
                if(!updatedImagesRoutes.contains(oldImageUrl)){
                    deleteImageFromDisk(oldImageUrl);
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

        if (currentUser.getWorkshop() == null || currentUser.getWorkshop().getWorkshopId() != (workshopId)) {
            throw new RuntimeException("Acceso denegado: No perteneces a este taller.");
        }

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        Workshop workshop = workshopJpaRepository.findById(workshopId).orElseThrow();

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
            throw new RuntimeException("No tienes permiso para aprobar el ingreso de este vehículo");
        }

        if(vehicle.getPendingWorkshop() == null){
            throw new RuntimeException("Este vehículo no tiene ninguna solicitud pendiente");
        }

        Workshop workshop = vehicle.getPendingWorkshop();

        vehicle.setWorkshop(vehicle.getPendingWorkshop());
        vehicle.setPendingWorkshop(null);

        Vehicle savedVehicle = vehicleJpaRepository.save(vehicle);

        try{
            User manager = userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER).orElse(null);
            System.out.println(manager);
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
            System.err.println(e.getMessage());
        }
        return NewVehicleDTO.of(savedVehicle);
    }

    public NewVehicleDTO rejectEntry(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(!vehicle.getOwner().getDni().equals(currentUser.getDni())){
            throw new RuntimeException("No tienes permiso para rechazar el ingreso de este vehículo");
        }

        vehicle.setPendingWorkshop(null);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO registerExit(String plate, Long workshopId, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || currentUser.getWorkshop().getWorkshopId() != (workshopId)) {
            throw new RuntimeException("Acceso denegado: No puedes registrar salidas de otro taller.");
        }

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        if(vehicle.getWorkshop() == null || vehicle.getWorkshop().getWorkshopId() != (workshopId)){
            throw new RuntimeException("No puedes dar salida a un coche que no esta registrado en el taller");
        }
        vehicle.setWorkshop(null);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO changeOwner(String plate, String currentOwnerId, String newOwnerId, String email){
        User currentUser = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (!currentUser.getDni().equals(currentOwnerId)) {
            throw new RuntimeException("Acceso denegado: Solo el dueño actual puede transferir el vehículo.");
        }

        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        User newOwner = userJpaRepository.findByDni(newOwnerId).orElseThrow(() -> new UserNotFoundException(newOwnerId));

        if(vehicle.getOwner() != null && vehicle.getOwner().getDni().equals(currentOwnerId)){
            vehicle.setOwner(newOwner);
        }else{
            throw new RuntimeException("Error: El DNI proporcionado no corresponde al dueño actual del vehículo.");
        }
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    @Transactional
    public NewVehicleDTO delete(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(vehicle.getOwner() == null || !vehicle.getOwner().getDni().equals(currentUser.getDni())){
            throw new RuntimeException("Acceso denegado: No tienes permiso para eliminar el vehículo");
        }
        NewVehicleDTO deletedVehicle = NewVehicleDTO.of(vehicle);

        if(vehicle.getImages() != null){
            for(String imageUrl : vehicle.getImages()){
                deleteImageFromDisk(imageUrl);
            }
        }

        vehicleJpaRepository.delete(vehicle);
        return deletedVehicle;
    }

    public List<NewWorkOrderResponseDTO> getVehicleHistory(String plate, String email){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        verifyVehicleReadAccess(vehicle, email);

        var result = workOrderJpaRepository.findByVehicle_Plate(plate);
        return result.stream().map(NewWorkOrderResponseDTO::of).toList();
    }

    private void verifyVehicleReadAccess(Vehicle vehicle, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (vehicle.getOwner() != null && vehicle.getOwner().getDni().equals(currentUser.getDni())) {
            return;
        }

        boolean isWorker = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER ||
                currentUser.getRole() == Role.MECHANIC;

        if (isWorker) {
            boolean inWorkshop = vehicle.getWorkshop() != null && currentUser.getWorkshop() != null &&
                    vehicle.getWorkshop().getWorkshopId() == (currentUser.getWorkshop().getWorkshopId());

            boolean pendingWorkshop = vehicle.getPendingWorkshop() != null && currentUser.getWorkshop() != null &&
                    vehicle.getPendingWorkshop().getWorkshopId() == (currentUser.getWorkshop().getWorkshopId());

            if (!inWorkshop && !pendingWorkshop) {
                throw new RuntimeException("Acceso denegado: El vehículo no se encuentra en tu taller.");
            }
        } else {
            throw new RuntimeException("Acceso denegado: Este vehículo no es tuyo.");
        }
    }

    public List<NewVehicleDTO> searchVehicles(String searchText, Long workshopId, String type, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // Si borran el texto, devolvemos la lista por defecto
        if (searchText == null || searchText.trim().isEmpty()) {
            if ("OWNER".equalsIgnoreCase(type)) return getByOwner(currentUser.getDni(), email);
            if ("ASSIGNED".equalsIgnoreCase(type)) return getVehiclesAssignedToMechanic(currentUser.getDni(), email);
            if ("WORKSHOP".equalsIgnoreCase(type)) return getByWorkshop(workshopId, email);
        }

        String text = searchText.toLowerCase();

        if ("OWNER".equalsIgnoreCase(type)) {
            var result = vehicleJpaRepository.searchByOwnerAndText(currentUser.getDni(), text);
            return result.stream().map(NewVehicleDTO::of).toList();

        } else if ("ASSIGNED".equalsIgnoreCase(type)) {
            List<WorkOrder> workOrders = workOrderJpaRepository.findByMechanic_Dni(currentUser.getDni());
            return workOrders.stream()
                    .filter(w -> w.getStatus() != null && !w.getStatus().toString().equals("COMPLETED"))
                    .map(WorkOrder::getVehicle)
                    .distinct()
                    .filter(v -> v.getPlate().toLowerCase().contains(text) ||
                            (v.getBrand() != null && v.getBrand().toLowerCase().contains(text)) ||
                            (v.getModel() != null && v.getModel().toLowerCase().contains(text))) // 🔥 Nueva condición
                    .map(NewVehicleDTO::of)
                    .toList();

        } else if ("WORKSHOP".equalsIgnoreCase(type)) {
            if (currentUser.getWorkshop() == null || currentUser.getWorkshop().getWorkshopId() != (workshopId)) {
                throw new RuntimeException("Acceso denegado: No perteneces a este taller.");
            }
            var result = vehicleJpaRepository.searchByWorkshopAndText(workshopId, text);
            return result.stream().map(NewVehicleDTO::of).toList();
        }

        throw new RuntimeException("Tipo de búsqueda no válido");
    }
}