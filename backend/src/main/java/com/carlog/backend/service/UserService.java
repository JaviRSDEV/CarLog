package com.carlog.backend.service;

import com.carlog.backend.DTO.NewUserDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.User;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;

    public List<User> getAll(){
        var result = userJpaRepository.findAll();
        if(result.isEmpty()) throw new UserNotFoundException();
        return result;
    }

    public User getByDni(String dni){
        return userJpaRepository.findByDni(dni).orElseThrow(() -> new UserNotFoundException(dni));
    }

    public List<User> getByName(String name){
        List<User> results = userJpaRepository.findByNameContainingIgnoreCase(name);
        if(results.isEmpty())
            throw new UserNotFoundException();

        return results;
    }

    public NewUserDTO add(NewUserDTO dto){
        var result = userJpaRepository.findByName(dto.name());
        if(result.isPresent()) throw new RuntimeException("Ya existe un empleado con el nombre y apellidos " + dto.name());
        if (dto.workShopId() != null) {
            workshopJpaRepository.findById(dto.workShopId())
                    .orElseThrow(() -> new WorkshopNotFoundException(dto.workShopId()));
        }

        var newUser = User.builder().dni(dto.dni()).name(dto.name()).email(dto.email()).phone(dto.phone()).password(dto.password()).role(dto.role()).mustChangePsswd(dto.mustChangePassword()).workShopId(dto.workShopId()).build();
        return NewUserDTO.of(userJpaRepository.save(newUser));
    }

    public NewUserDTO edit(NewUserDTO dto, String Dni){
        return userJpaRepository.findByDni(Dni).map(user -> {
            user.setDni(dto.dni());
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setPhone(dto.phone());
            user.setPassword(dto.password());
            user.setRole(dto.role());
            user.setMustChangePsswd(dto.mustChangePassword());
            user.setWorkShopId(dto.workShopId());
            return NewUserDTO.of(userJpaRepository.save(user));
        }).orElseThrow( () -> new UserNotFoundException());
    }

    public NewUserDTO delete(String dni){
        var result = userJpaRepository.findByDni(dni);
        if(result.isEmpty()) throw new UserNotFoundException();
        userJpaRepository.deleteByDni(dni);
        return NewUserDTO.of(result.get());
    }
}
