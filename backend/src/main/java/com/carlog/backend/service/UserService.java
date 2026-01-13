package com.carlog.backend.service;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
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
        Workshop workshop = null;
        Role roleToSave = Role.CLIENT;
        if(userJpaRepository.findByDni(dto.dni()).isPresent()) throw new RuntimeException("Ya existe un empleado con el DNI " + dto.dni());
        //Evitamos que alguien pueda auto contratarse en un taller
        if(dto.workShopId() != null){
            throw new RuntimeException("No puedes unirte a un taller durante el registro, debes ser contratado por uno");
        }
        //Si el rol es diferente a null le asignamos el pasado por el dto
        if(dto.role() != null)
            roleToSave = dto.role();
        //Si el rol elegido es co_manager o mechanic en el registro, el sistema por seguridad asignara el rol de cliente
        if(roleToSave == Role.CO_MANAGER || roleToSave == Role.MECHANIC){
            roleToSave = Role.CLIENT;
        }
        //comprobamos que la asignación de taller sea con un taller existente
        if (dto.workShopId() != null) {
             workshop = workshopJpaRepository.findById(dto.workShopId())
                    .orElseThrow(() -> new WorkshopNotFoundException(dto.workShopId()));
        }

        var newUser = User.builder().dni(dto.dni()).name(dto.name()).email(dto.email()).phone(dto.phone()).password(dto.password()).role(dto.role()).mustChangePsswd(dto.mustChangePassword()).workshop(workshop).build();
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

            if (dto.workShopId() != null) {
               Workshop w = workshopJpaRepository.findById(dto.workShopId())
                        .orElseThrow(() -> new WorkshopNotFoundException(dto.workShopId()));
               user.setWorkshop(w);
            }else{
                user.setWorkshop(null);
            }
            return NewUserDTO.of(userJpaRepository.save(user));
        }).orElseThrow( () -> new UserNotFoundException());
    }

    public NewUserDTO delete(String dni){
        var result = userJpaRepository.findByDni(dni);
        if(result.isEmpty()) throw new UserNotFoundException();
        userJpaRepository.deleteByDni(dni);
        return NewUserDTO.of(result.get());
    }

    public NewUserDTO promoteToWorker

            (String managerDni, String employeeDni, Role newRole){

        User manager = userJpaRepository.findByDni(managerDni).orElseThrow(() -> new UserNotFoundException(managerDni));
        //comprobamos que el que va a realizar la contratación es un manager
        if(manager.getRole() != Role.MANAGER || manager.getWorkshop() == null){
            throw new RuntimeException("Solo un manager con taller puede realiza contrataciones");
        }
        //Buscamos el trabajador en la base de datos con el dni proporcionado
        User employee = userJpaRepository.findByDni(employeeDni).orElseThrow(() -> new UserNotFoundException(employeeDni));
        //Comprobamos que el usuario no trabaje en otro taller
        if(employee.getWorkshop() != null){
            throw new RuntimeException("Este usuario ya trabaja en otro taller");
        }
        //Comprobamos que el rol para la contratación o es mechanic o co_manager
        if(newRole != Role.MECHANIC && newRole != Role.CO_MANAGER){
            throw new RuntimeException("Rol inválido para contratación");
        }

        employee.setRole(newRole);
        employee.setWorkshop(manager.getWorkshop());
        return NewUserDTO.of(userJpaRepository.save(employee));
    }
}
