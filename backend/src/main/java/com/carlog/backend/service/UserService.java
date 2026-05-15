package com.carlog.backend.service;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.dto.WorkshopHiringEvent;
import com.carlog.backend.error.*;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ApplicationEventPublisher eventPublisher;

    private static final String NOTIF_TOPIC_PREFIX = "/topic/notificaciones/";

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
        Role roleToSave = Role.CLIENT;

        if(userJpaRepository.findByDni(dto.dni()).isPresent())
            throw new UserAlreadyExistsException("Ya existe un empleado con el DNI " + dto.dni());

        if(dto.workShopId() != null){
            throw new InvalidRegistrationException("No puedes unirte a un taller durante el registro, debes ser contratado por uno");
        }

        if(dto.role() != null)
            roleToSave = dto.role();

        if(roleToSave != Role.CLIENT || roleToSave != Role.MANAGER){
            roleToSave = Role.CLIENT;
        }

        var newUser = User.builder()
                .dni(dto.dni())
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .role(roleToSave)
                .workshop(null)
                .build();

        return NewUserDTO.of(userJpaRepository.save(newUser));
    }

    public NewUserDTO edit(NewUserDTO dto, String dni, String email){
        User userEditing = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return userJpaRepository.findByDni(dni).map(user -> {
            boolean isSelf = user.getDni().equals(userEditing.getDni());
            boolean isManagerOfEmployee = userEditing.getRole() == Role.MANAGER &&
                    userEditing.getWorkshop() != null &&
                    userEditing.getWorkshop().equals(user.getWorkshop());

            if (!isSelf && !isManagerOfEmployee) {
                throw new UnauthorizedActionException("No tienes permisos para editar a este usuario.");
            }

            user.setDni(dto.dni());
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setPhone(dto.phone());

            if (isManagerOfEmployee && dto.role() != null) {
                user.setRole(dto.role());
            }

            return NewUserDTO.of(userJpaRepository.save(user));
        }).orElseThrow(UserNotFoundException::new); // 4. Cambiado lambda por Method Reference
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
            throw new UnauthorizedActionException("Acceso denegado.");
        }

        Workshop workshop = workshopJpaRepository.findById(id)
                .orElseThrow(() -> new WorkshopNotFoundException("Taller no encontrado"));

        return workshop.getEmployees().stream()
                .map(NewUserDTO::of).toList();
    }

    @Transactional
    public void inviteToWorkshop(String managerEmail, String employeeDni, Role role) {
        User manager = userJpaRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new UserNotFoundException(managerEmail));

        if ((manager.getRole() != Role.MANAGER && manager.getRole() != Role.CO_MANAGER) || manager.getWorkshop() == null) {
            throw new UnauthorizedActionException("Acceso denegado: Solo los administradores de un taller pueden invitar.");
        }

        User employee = userJpaRepository.findByDni(employeeDni)
                .orElseThrow(() -> new UserNotFoundException(employeeDni));

        if (employee.getWorkshop() != null) {
            throw new UserAlreadyHasWorkshopException(employeeDni);
        }

        employee.setPendingWorkshop(manager.getWorkshop());
        employee.setPendingRole(role);
        User savedEmployee = userJpaRepository.save(employee);

        eventPublisher.publishEvent(WorkshopHiringEvent.of(savedEmployee));

        try {
            NotificationDTO notif = NotificationDTO.builder()
                    .type("INVITE")
                    .title("¡Nueva oferta de empleo!")
                    .message("El taller " + manager.getWorkshop().getWorkshopName() + " quiere contratarte como " + role.name())
                    .extraData(manager.getWorkshop().getWorkshopId().toString())
                    .build();

            messagingTemplate.convertAndSend(NOTIF_TOPIC_PREFIX + employeeDni, notif);
            log.info("Notificación WebSocket enviada al usuario con DNI: {}", employeeDni);
        } catch (Exception e) {
            log.error("Error al enviar WebSocket de invitación: {}", e.getMessage());
        }
    }

    public NewUserDTO acceptInvitation(String email){
        User user = userJpaRepository.findByEmail(email).orElseThrow();
        if(user.getPendingWorkshop() == null)
            throw new NoPendingInvitationException("No hay ninguna invitación");

        Workshop workshop = user.getPendingWorkshop();
        user.setWorkshop(workshop);
        user.setRole(user.getPendingRole());
        user.setPendingWorkshop(null);
        user.setPendingRole(null);

        User savedUser = userJpaRepository.save(user);
        try{
            User manager = userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER).orElse(null);

            if(manager != null){
                NotificationDTO alert = NotificationDTO.builder()
                        .type("NEW_EMPLOYEE")
                        .title("¡Nuevo empleado!")
                        .message("Un nuevo mecánico aceptó la invitación")
                        .extraData(savedUser.getDni())
                        .build();

                messagingTemplate.convertAndSend(NOTIF_TOPIC_PREFIX + manager.getDni(), alert);
            }
        }catch (Exception e){
            log.error("Error enviando notificación: {}", e.getMessage());
        }
        return NewUserDTO.of(savedUser);
    }

    public NewUserDTO rejectInvitation(String email){
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        user.setPendingWorkshop(null);
        user.setPendingRole(null);
        userJpaRepository.save(user);

        return NewUserDTO.of(user);
    }

    public void fireEmployee(String managerEmail, String employeeDni){
        User manager = userJpaRepository.findByEmail(managerEmail)
                .orElseThrow(() -> new UserNotFoundException(managerEmail));

        User employee = userJpaRepository.findByDni(employeeDni)
                .orElseThrow(() -> new UserNotFoundException(employeeDni));

        if (manager.getWorkshop() == null || !manager.getWorkshop().equals(employee.getWorkshop())) {
            throw new UnauthorizedActionException("No pertenece a tu taller.");
        }

        employee.setWorkshop(null);
        employee.setRole(Role.CLIENT);
        userJpaRepository.save(employee);

        NotificationDTO notif = NotificationDTO.builder()
                .type("FIRE")
                .title("Permisos revocados")
                .message("Has sido despedido del taller.")
                .build();

        messagingTemplate.convertAndSend(NOTIF_TOPIC_PREFIX + employeeDni, notif);
    }
}