package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkshopService {

    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;

    private final Cloudinary cloudinary;

    public List<NewWorkshopDTO> getAll(){
        var result = workshopJpaRepository.findAll();
        return result.stream().map(NewWorkshopDTO::of).toList();
    }

    public NewWorkshopDTO getWorkshopById(Long id, String email){
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(id));

        verifyWorkshopReadAccess(workshop, email);
        return NewWorkshopDTO.of(workshop);
    }

    @Transactional
    public NewWorkshopDTO add(NewWorkshopDTO dto, String email) {
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if (result.isPresent()) throw new RuntimeException("Ya existe un taller con ese nombre " + dto.workshopName());

        User workshopOwner = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if(workshopOwner.getWorkshop() != null){
            throw new RuntimeException("Este usuario ya es administrador de otro taller");
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

        boolean isManagerOrCoManager = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER;

        if (!isManagerOrCoManager) {
            throw new RuntimeException("Acceso denegado.");
        }

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(workshop.getWorkshopId())) {
            throw new RuntimeException("Acceso denegado: No eres el responsable de este taller.");
        }
    }

    private void verifyWorkshopReadAccess(Workshop workshop, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null ||
                !currentUser.getWorkshop().getWorkshopId().equals(workshop.getWorkshopId())) {
            throw new SecurityException("Acceso denegado: No perteneces a este taller.");
        }
    }
}