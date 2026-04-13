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

    public NewWorkshopDTO getByWorkshopName(String name){
        Workshop workshop = workshopJpaRepository.findByWorkshopName(name)
                .orElseThrow(() -> new WorkshopNotFoundException(name));
        return NewWorkshopDTO.of(workshop);
    }

    public NewWorkshopDTO getWorkshopById(Long id){
        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
        return NewWorkshopDTO.of(workshop);
    }

    @Transactional
    public NewWorkshopDTO add(NewWorkshopDTO dto, String email){
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if(result.isPresent()) throw new RuntimeException("Ya existe un taller con ese nombre " + dto.workshopName());

        User workshopOwner = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        var newWorkshop = Workshop.builder()
                .workshopName(dto.workshopName())
                .address(dto.address())
                .workshopPhone(dto.workshopPhone())
                .workshopEmail(dto.workshopEmail())
                .icon(dto.icon())
                .build();

        newWorkshop = workshopJpaRepository.save(newWorkshop);

        workshopOwner.setWorkshop(newWorkshop);
        userJpaRepository.save(workshopOwner);

        return NewWorkshopDTO.of(newWorkshop);
    }

    public NewWorkshopDTO edit(NewWorkshopDTO dto, String name, String email){
        return workshopJpaRepository.findByWorkshopName(name).map(workshop -> {

            verifyWorkshopManagerAccess(workshop, email);

            if(!workshop.getWorkshopName().equalsIgnoreCase(dto.workshopName())){
                var existingWorkshop = workshopJpaRepository.findByWorkshopName(dto.workshopName());
                if(existingWorkshop.isPresent()){
                    throw new RuntimeException("Error: ya existe otro taller registrado con ese nombre");
                }
            }

            workshop.setWorkshopName(dto.workshopName());
            workshop.setAddress(dto.address());
            workshop.setWorkshopPhone(dto.workshopPhone());
            workshop.setWorkshopEmail(dto.workshopEmail());
            workshop.setIcon(dto.icon());

            return NewWorkshopDTO.of(workshopJpaRepository.save(workshop));
        }).orElseThrow(() -> new WorkshopNotFoundException(name));
    }

    @Transactional
    public NewWorkshopDTO delete(String name, String email){
        Workshop workshop = workshopJpaRepository.findByWorkshopName(name)
                .orElseThrow(() -> new WorkshopNotFoundException(name));

        verifyWorkshopManagerAccess(workshop, email);

        workshopJpaRepository.deleteByWorkshopName(name);
        return NewWorkshopDTO.of(workshop);
    }

    private void verifyWorkshopManagerAccess(Workshop workshop, String email) {
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        boolean isManagerOrCoManager = currentUser.getRole() == Role.MANAGER ||
                currentUser.getRole() == Role.CO_MANAGER;

        if (!isManagerOrCoManager) {
            throw new RuntimeException("Acceso denegado: Solo los administradores del taller pueden modificarlo.");
        }

        if (currentUser.getWorkshop() == null ||
                currentUser.getWorkshop().getWorkshopId() != (workshop.getWorkshopId())) {
            throw new RuntimeException("Acceso denegado: No tienes permiso para administrar este taller.");
        }
    }
}