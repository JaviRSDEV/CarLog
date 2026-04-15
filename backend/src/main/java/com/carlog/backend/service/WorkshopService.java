package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<NewWorkshopDTO> getAll(){
        var result = workshopJpaRepository.findAll();
        return result.stream().map(NewWorkshopDTO::of).toList();
    }

    public NewWorkshopDTO getWorkshopById(Long id){
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
        return NewWorkshopDTO.of(workshop);
    }

    @Transactional
    public NewWorkshopDTO add(NewWorkshopDTO dto, String email) {
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if (result.isPresent()) throw new RuntimeException("Ya existe un taller con ese nombre " + dto.workshopName());

        User workshopOwner = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        String iconUrl = dto.icon();
        if (iconUrl != null && iconUrl.startsWith("data:image")) {
            iconUrl = saveBase64ImageOnDisk(iconUrl, dto.workshopName().replace(" ", "_"));
        }

        var newWorkshop = Workshop.builder()
                .workshopName(dto.workshopName())
                .address(dto.address())
                .workshopPhone(dto.workshopPhone())
                .workshopEmail(dto.workshopEmail())
                .icon(iconUrl)
                .build();

        newWorkshop = workshopJpaRepository.save(newWorkshop);
        workshopOwner.setWorkshop(newWorkshop);
        userJpaRepository.save(workshopOwner);

        return NewWorkshopDTO.of(newWorkshop);
    }

    @Transactional
    public NewWorkshopDTO edit(NewWorkshopDTO dto, Long id, MultipartFile file, String email) {
        return workshopJpaRepository.findById(id).map(workshop -> {
            verifyWorkshopManagerAccess(workshop, email);

            if (!workshop.getWorkshopName().equalsIgnoreCase(dto.workshopName())) {
                if (workshopJpaRepository.findByWorkshopName(dto.workshopName()).isPresent()) {
                    throw new RuntimeException("Error: ya existe otro taller registrado con ese nombre");
                }
            }

            if (file != null && !file.isEmpty()) {
                deleteImageFromDisk(workshop.getIcon());
                workshop.setIcon(saveMultipartFileOnDisk(file));
            }
            else if (dto.icon() == null || dto.icon().isEmpty()) {
                deleteImageFromDisk(workshop.getIcon());
                workshop.setIcon(null);
            }

            workshop.setWorkshopName(dto.workshopName());
            workshop.setAddress(dto.address());
            workshop.setWorkshopPhone(dto.workshopPhone());
            workshop.setWorkshopEmail(dto.workshopEmail());

            return NewWorkshopDTO.of(workshopJpaRepository.save(workshop));
        }).orElseThrow(() -> new WorkshopNotFoundException(id));
    }

    @Transactional
    public NewWorkshopDTO delete(Long id, String email) {
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(id));

        verifyWorkshopManagerAccess(workshop, email);
        String iconUrl = workshop.getIcon();

        workshopJpaRepository.delete(workshop);

        if (iconUrl != null) {
            deleteImageFromDisk(iconUrl);
        }

        return NewWorkshopDTO.of(workshop);
    }

    private String saveMultipartFileOnDisk(MultipartFile file) {
        try {
            Path directory = Paths.get("uploads");
            if (!Files.exists(directory)) Files.createDirectories(directory);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(" ", "_");
            Path absolutePath = directory.resolve(fileName);

            Files.copy(file.getInputStream(), absolutePath, StandardCopyOption.REPLACE_EXISTING);

            return "http://localhost:8081/uploads/" + fileName;
        } catch (IOException e) {
            return null;
        }
    }

    private String saveBase64ImageOnDisk(String base64Image, String namePrefix) {
        try {
            String extension = ".webp";
            if (base64Image.contains("data:image/png")) extension = ".png";
            else if (base64Image.contains("data:image/jpeg")) extension = ".jpeg";

            String data = base64Image.split(",")[1];
            byte[] imageBytes = java.util.Base64.getDecoder().decode(data);

            Path directory = Paths.get("uploads");
            if (!Files.exists(directory)) Files.createDirectories(directory);

            String fileName = namePrefix + "_" + System.currentTimeMillis() + extension;
            Path absolutePath = directory.resolve(fileName);
            Files.write(absolutePath, imageBytes);

            return "http://localhost:8081/uploads/" + fileName;
        } catch (IOException e) {
            return null;
        }
    }

    private void deleteImageFromDisk(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("/uploads/")) {
            return;
        }

        try {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Archivo eliminado del disco: " + filePath);
            } else {
                System.out.println("El archivo no se encontró físicamente: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar archivo físico: " + e.getMessage());
        }
    }

    private void verifyWorkshopManagerAccess(Workshop workshop, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isManagerOrCoManager = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER;

        if (!isManagerOrCoManager) {
            throw new RuntimeException("Acceso denegado.");
        }

        if (currentUser.getWorkshop() == null ||
                currentUser.getWorkshop().getWorkshopId() != (workshop.getWorkshopId())) {
            throw new RuntimeException("Acceso denegado: No eres el responsable de este taller.");
        }
    }
}