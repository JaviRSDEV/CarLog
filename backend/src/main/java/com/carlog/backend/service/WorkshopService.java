package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.WorkshopJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopJpaRepository workshopJpaRepository;

    public List<Workshop> getAll(){
        var result = workshopJpaRepository.findAll();
        if(result.isEmpty())
            throw new WorkshopNotFoundException();
        return result;
    }

    public Workshop getByWorkshopName(String name){
        return workshopJpaRepository.findByWorkshopName(name).orElseThrow(() -> new WorkshopNotFoundException(name));
    }

    public NewWorkshopDTO add(NewWorkshopDTO dto){
        var result = workshopJpaRepository.findByWorkshopName(dto.workshopName());
        if(result.isPresent()) throw new RuntimeException("Ya existe un taller con ese nombre " + dto.workshopName());
        var newWorkshop = Workshop.builder().workshopName(dto.workshopName()).address(dto.address()).workshopPhone(dto.workshopPhone()).workshopEmail(dto.workshopEmail()).icon(dto.icon()).build();
        return NewWorkshopDTO.of(workshopJpaRepository.save(newWorkshop));
    }

    public NewWorkshopDTO edit(NewWorkshopDTO dto, String name){
        return workshopJpaRepository.findByWorkshopName(name).map(workshop -> {
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

    public List<User> getEmployeesByWorkshopId(Long id){
        if(!workshopJpaRepository.existsById(id)) throw new WorkshopNotFoundException(id);
        var result = workshopJpaRepository.findUserByWorkshopId(id);
        return result;
    }
}
