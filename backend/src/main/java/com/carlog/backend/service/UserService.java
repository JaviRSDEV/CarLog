package com.carlog.backend.service;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<NewUserDTO> getAll(){
        var result = userJpaRepository.findAll();
        if(result.isEmpty()) throw new UserNotFoundException();
        return result.stream().map(NewUserDTO::of).toList();
    }

    public NewUserDTO getByDni(String dni){
        User user = userJpaRepository.findByDni(dni).orElseThrow(() -> new UserNotFoundException(dni));
        return NewUserDTO.of(user);
    }

    public NewUserDTO getMyProfile(){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return getByDni(currentUser.getDni());
    }

    public List<NewUserDTO> getByName(String name){
        List<User> results = userJpaRepository.findByNameContainingIgnoreCase(name);
        if(results.isEmpty())
            throw new UserNotFoundException();

        return results.stream().map(NewUserDTO::of).toList();
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

        var newUser = User.builder().dni(dto.dni()).name(dto.name()).email(dto.email()).phone(dto.phone()).role(roleToSave).mustChangePsswd(dto.mustChangePassword()).workshop(workshop).build();
        return NewUserDTO.of(userJpaRepository.save(newUser));
    }

    public NewUserDTO edit(NewUserDTO dto, String Dni, String email){
        User userEditing = userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        return userJpaRepository.findByDni(Dni).map(user -> {
            boolean isSelf = user.getDni().equals(userEditing.getDni());
            boolean isManagerOfEmployee = userEditing.getRole() == Role.MANAGER &&
                    userEditing.getWorkshop() != null &&
                    userEditing.getWorkshop().equals(user.getWorkshop());

            if (!isSelf && !isManagerOfEmployee) {
                throw new SecurityException("No tienes permisos para editar a este usuario.");
            }

            user.setDni(dto.dni());
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setPhone(dto.phone());
            user.setMustChangePsswd(dto.mustChangePassword());

            if (isManagerOfEmployee) {
                if (dto.role() != null) user.setRole(dto.role());
            }

            return NewUserDTO.of(userJpaRepository.save(user));
        }).orElseThrow(() -> new UserNotFoundException());
    }

    public NewUserDTO delete(String dni){
        var result = userJpaRepository.findByDni(dni);
        if(result.isEmpty()) throw new UserNotFoundException();
        userJpaRepository.deleteByDni(dni);
        return NewUserDTO.of(result.get());
    }

    public List<NewUserDTO> getEmployeesByWorkshopId(Long id, String email){
        User currentUser = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (currentUser.getWorkshop() == null || !currentUser.getWorkshop().getWorkshopId().equals(id)) {
            throw new SecurityException("Acceso denegado: No puedes ver los empleados de un taller al que no perteneces.");
        }

        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException(id));

        return workshop.getEmployees().stream()
                .map(NewUserDTO::of).toList();
    }

    public void inviteToWorkshop(String managerEmail, String employeeDni, Role role){
        User manager = userJpaRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new UserNotFoundException(managerEmail));

        if ((manager.getRole() != Role.MANAGER && manager.getRole() != Role.CO_MANAGER) || manager.getWorkshop() == null) {
            throw new SecurityException("Solo los administradores de un taller pueden invitar empleados.");
        }

        User employee = userJpaRepository.findByDni(employeeDni)
                .orElseThrow(() -> new UserNotFoundException(employeeDni));

        if(employee.getWorkshop() != null) throw new RuntimeException("El usuario ya se encuentra trabajando en un taller");

        employee.setPendingWorkshop(manager.getWorkshop());
        employee.setPendingRole(role);
        userJpaRepository.save(employee);

        NotificationDTO notif = NotificationDTO.builder()
                .type("INVITE")
                .title("¡Nueva oferta de empleo!")
                .message("El taller " + manager.getWorkshop().getWorkshopName() + " quiere contratarte")
                .build();

        messagingTemplate.convertAndSend("/topic/notificaciones/" + employeeDni, notif);
    }

    public NewUserDTO acceptInvitation(String email){
        User user = userJpaRepository.findByEmail(email).orElseThrow();
        if(user.getPendingWorkshop() == null) throw new RuntimeException("No hay ninguna invitación");

        Workshop workshop = user.getPendingWorkshop();

        user.setWorkshop(user.getPendingWorkshop());
        user.setRole(user.getPendingRole());

        user.setPendingWorkshop(null);
        user.setPendingRole(null);

        User savedUser = userJpaRepository.save(user);
        try{
            User manager = userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER).orElse(null);

            if(manager != null){
                NotificationDTO alert = NotificationDTO.builder()
                        .type("NEW_EMPLOYEE")
                        .title("¡Nuevo empleado en el taller!")
                        .message("Un nuevo mecánico acepto la invitación para trabajar")
                        .extraData(savedUser.getDni())
                        .build();

                messagingTemplate.convertAndSend("/topic/notificaciones/" + manager.getDni(), alert);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        return NewUserDTO.of(savedUser);
    }

    public void rejectInvitation(String email){
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        user.setPendingWorkshop(null);
        user.setPendingRole(null);
        userJpaRepository.save(user);
    }

    public void fireEmployee(String managerEmail, String employeeDni){
        User manager = userJpaRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new UserNotFoundException(managerEmail));

        User employee = userJpaRepository.findByDni(employeeDni)
                .orElseThrow(() -> new UserNotFoundException(employeeDni));

        if (manager.getWorkshop() == null || !manager.getWorkshop().equals(employee.getWorkshop())) {
            throw new SecurityException("No puedes despedir a un empleado que no pertenece a tu taller.");
        }

        employee.setWorkshop(null);
        employee.setRole(Role.CLIENT);
        userJpaRepository.save(employee);

        NotificationDTO notif = NotificationDTO.builder()
                .type("FIRE")
                .title("Permisos revocados")
                .message("Has sido despedido del taller.")
                .build();

        messagingTemplate.convertAndSend("/topic/notificaciones/" + employeeDni, notif);
    }
}
