package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<NewWorkshopDTO> getAll(){
        var result = workshopJpaRepository.findAll();
        if(result.isEmpty())
            throw new WorkshopNotFoundException();
        return result.stream().map(NewWorkshopDTO::of).toList();
    }

    public NewWorkshopDTO getByWorkshopName(String name){
        Workshop workshop = workshopJpaRepository.findByWorkshopName(name).orElseThrow(() -> new WorkshopNotFoundException(name));

        return NewWorkshopDTO.of(workshop);
    }

    public NewWorkshopDTO add(NewWorkshopDTO dto){
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if(result.isPresent()) throw new RuntimeException("Ya existe un taller con ese nombre " + dto.workshopName());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUserEmail;

        if(principal instanceof UserDetails){
            loggedUserEmail = ((UserDetails) principal).getUsername();
        }else{
            loggedUserEmail = principal.toString();
        }

        User workshopOwner = userJpaRepository.findByEmail(loggedUserEmail).orElseThrow(() -> new UserNotFoundException());

        var newWorkshop = Workshop.builder().workshopName(dto.workshopName()).address(dto.address()).workshopPhone(dto.workshopPhone()).workshopEmail(dto.workshopEmail()).icon(dto.icon()).build();

        userJpaRepository.save(workshopOwner);
        workshopOwner.setWorkshop(newWorkshop);
        return NewWorkshopDTO.of(workshopJpaRepository.save(newWorkshop));
    }

    public NewWorkshopDTO edit(NewWorkshopDTO dto, String name){
        return workshopJpaRepository.findByWorkshopName(name).map(workshop -> {
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
    public NewWorkshopDTO delete(String name){
        var result = workshopJpaRepository.findByWorkshopName(name);
        if(result.isEmpty()) throw new WorkshopNotFoundException(name);
        workshopJpaRepository.deleteByWorkshopName(name);
        return NewWorkshopDTO.of(result.get());
    }
}
