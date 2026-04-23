package com.carlog.backend.service;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NotificationDTO;
import com.carlog.backend.error.*;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock private UserJpaRepository userJpaRepository;
    @Mock private WorkshopJpaRepository workshopJpaRepository;
    @Mock private SimpMessagingTemplate messagingTemplate;

    @InjectMocks private UserService userService;

    @Captor private ArgumentCaptor<User> userCaptor;

    private User clientUser;
    private User managerUser;
    private User employeeUser;
    private Workshop workshop;

    @BeforeEach
    void setUp() {
        workshop = Workshop.builder().workshopId(1L).workshopName("Taller Test").build();

        clientUser = User.builder().dni("11111111A").name("Cliente").email("client@test.com").role(Role.CLIENT).build();

        managerUser = User.builder().dni("22222222B").name("Manager").email("manager@test.com").role(Role.MANAGER).workshop(workshop).build();

        employeeUser = User.builder().dni("33333333C").name("Empleado").email("emp@test.com").role(Role.MECHANIC).workshop(workshop).build();

        workshop.setEmployees(List.of(managerUser, employeeUser));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getByDni_Exists_ReturnsUser() {
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));
        NewUserDTO result = userService.getByDni(clientUser.getDni());
        assertEquals(clientUser.getDni(), result.dni());
    }

    @Test
    void getByDni_NotExists_ThrowsException() {
        when(userJpaRepository.findByDni("999")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getByDni("999"));
    }

    @Test
    void getMyProfile_AuthenticatedUser_ReturnsProfile() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(clientUser);
        SecurityContextHolder.setContext(securityContext);

        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));

        NewUserDTO result = userService.getMyProfile();
        assertEquals(clientUser.getDni(), result.dni());
    }

    @Test
    void getByName_MatchesFound_ReturnsList() {
        when(userJpaRepository.findByNameContainingIgnoreCase("Cli")).thenReturn(List.of(clientUser));
        List<NewUserDTO> result = userService.getByName("Cli");
        assertFalse(result.isEmpty());
        assertEquals("Cliente", result.get(0).name());
    }

    @Test
    void getByName_NoMatches_ThrowsException() {
        when(userJpaRepository.findByNameContainingIgnoreCase("Fantasma")).thenReturn(Collections.emptyList());
        assertThrows(UserNotFoundException.class, () -> userService.getByName("Fantasma"));
    }

    @Test
    void add_DniAlreadyExists_ThrowsException() {
        NewUserDTO dto = new NewUserDTO(clientUser.getDni(), "X", "X", "X", null, null, null, null);
        when(userJpaRepository.findByDni(dto.dni())).thenReturn(Optional.of(clientUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.add(dto));
    }

    @Test
    void add_UserTriesToAutoHire_ThrowsException() {
        NewUserDTO dto = new NewUserDTO("88888888X", "X", "X", "X", Role.CLIENT, 1L, null, null);

        assertThrows(InvalidRegistrationException.class, () -> userService.add(dto));
    }

    @Test
    void add_RoleIsNull_DefaultsToClient() {
        NewUserDTO dto = new NewUserDTO("88888888X", "Nuevo", "X", "X", null, null, null, null);
        when(userJpaRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        NewUserDTO result = userService.add(dto);
        assertEquals(Role.CLIENT, result.role());
    }

    @Test
    void add_RoleIsMechanicOrCoManager_ForcesToClient() {
        NewUserDTO dto = new NewUserDTO("88888888X", "Nuevo", "X", "X", Role.MECHANIC, null, null, null);
        when(userJpaRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        NewUserDTO result = userService.add(dto);
        assertEquals(Role.CLIENT, result.role());
    }

    @Test
    void edit_UserEditsThemselves_Success() {
        NewUserDTO dto = new NewUserDTO(clientUser.getDni(), "Cliente Modificado", "X", "X", null, null, null, null);
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));
        when(userJpaRepository.save(any(User.class))).thenReturn(clientUser);

        userService.edit(dto, clientUser.getDni(), clientUser.getEmail());
        verify(userJpaRepository).save(argThat(u -> u.getName().equals("Cliente Modificado")));
    }

    @Test
    void edit_ManagerEditsEmployeeAndChangesRole_Success() {
        NewUserDTO dto = new NewUserDTO(employeeUser.getDni(), "Empleado Modificado", "X", "X", Role.CO_MANAGER, null, null, null);
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(userJpaRepository.findByDni(employeeUser.getDni())).thenReturn(Optional.of(employeeUser));
        when(userJpaRepository.save(any(User.class))).thenReturn(employeeUser);

        userService.edit(dto, employeeUser.getDni(), managerUser.getEmail());

        verify(userJpaRepository).save(userCaptor.capture());
        assertEquals(Role.CO_MANAGER, userCaptor.getValue().getRole());
    }

    @Test
    void edit_IntruderTriesToEdit_ThrowsSecurityException() {
        NewUserDTO dto = new NewUserDTO(managerUser.getDni(), "Hackeado", "X", "X", null, null, null, null);
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(userJpaRepository.findByDni(managerUser.getDni())).thenReturn(Optional.of(managerUser));

        assertThrows(UnauthorizedActionException.class, () -> userService.edit(dto, managerUser.getDni(), clientUser.getEmail()));
    }

    @Test
    void delete_Exists_DeletesUser() {
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));
        userService.delete(clientUser.getDni());
        verify(userJpaRepository).deleteByDni(clientUser.getDni());
    }

    @Test
    void getEmployeesByWorkshopId_ValidManager_ReturnsList() {
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(workshopJpaRepository.findById(workshop.getWorkshopId())).thenReturn(Optional.of(workshop));

        List<NewUserDTO> result = userService.getEmployeesByWorkshopId(workshop.getWorkshopId(), managerUser.getEmail());
        assertEquals(2, result.size());
    }

    @Test
    void getEmployeesByWorkshopId_Intruder_ThrowsSecurityException() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        assertThrows(UnauthorizedActionException.class, () -> userService.getEmployeesByWorkshopId(workshop.getWorkshopId(), clientUser.getEmail()));
    }

    @Test
    void inviteToWorkshop_ValidManager_SetsPendingAndNotifies() {
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(userJpaRepository.findByDni(clientUser.getDni())).thenReturn(Optional.of(clientUser));

        userService.inviteToWorkshop(managerUser.getEmail(), clientUser.getDni(), Role.MECHANIC);

        verify(userJpaRepository).save(userCaptor.capture());
        assertEquals(workshop, userCaptor.getValue().getPendingWorkshop());
        assertEquals(Role.MECHANIC, userCaptor.getValue().getPendingRole());
        verify(messagingTemplate).convertAndSend(eq("/topic/notificaciones/" + clientUser.getDni()), any(NotificationDTO.class));
    }

    @Test
    void inviteToWorkshop_EmployeeAlreadyWorking_ThrowsException() {
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(userJpaRepository.findByDni(employeeUser.getDni())).thenReturn(Optional.of(employeeUser));

        assertThrows(UserAlreadyHasWorkshopException.class, () -> userService.inviteToWorkshop(managerUser.getEmail(), employeeUser.getDni(), Role.MECHANIC));
    }

    @Test
    void acceptInvitation_HasPending_AcceptsAndNotifiesManager() {
        clientUser.setPendingWorkshop(workshop);
        clientUser.setPendingRole(Role.MECHANIC);

        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(userJpaRepository.save(any(User.class))).thenReturn(clientUser);
        when(userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER)).thenReturn(Optional.of(managerUser));

        userService.acceptInvitation(clientUser.getEmail());

        verify(userJpaRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(workshop, savedUser.getWorkshop());
        assertEquals(Role.MECHANIC, savedUser.getRole());
        assertNull(savedUser.getPendingWorkshop());

        verify(messagingTemplate).convertAndSend(eq("/topic/notificaciones/" + managerUser.getDni()), any(NotificationDTO.class));
    }

    @Test
    void acceptInvitation_NoPending_ThrowsException() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        assertThrows(NoPendingInvitationException.class, () -> userService.acceptInvitation(clientUser.getEmail()));
    }

    @Test
    void rejectInvitation_ClearsPendingData() {
        clientUser.setPendingWorkshop(workshop);
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        userService.rejectInvitation(clientUser.getEmail());

        verify(userJpaRepository).save(userCaptor.capture());
        assertNull(userCaptor.getValue().getPendingWorkshop());
    }

    @Test
    void fireEmployee_ValidManager_FiresAndNotifies() {
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(userJpaRepository.findByDni(employeeUser.getDni())).thenReturn(Optional.of(employeeUser));

        userService.fireEmployee(managerUser.getEmail(), employeeUser.getDni());

        verify(userJpaRepository).save(userCaptor.capture());
        User firedUser = userCaptor.getValue();

        assertNull(firedUser.getWorkshop());
        assertEquals(Role.CLIENT, firedUser.getRole());
        verify(messagingTemplate).convertAndSend(eq("/topic/notificaciones/" + employeeUser.getDni()), any(NotificationDTO.class));
    }

    @Test
    void fireEmployee_ManagerFromOtherWorkshop_ThrowsSecurityException() {
        Workshop otherWorkshop = Workshop.builder().workshopId(99L).build();
        User fakeManager = User.builder().dni("999").email("fake@manager.com").role(Role.MANAGER).workshop(otherWorkshop).build();

        when(userJpaRepository.findByEmail(fakeManager.getEmail())).thenReturn(Optional.of(fakeManager));
        when(userJpaRepository.findByDni(employeeUser.getDni())).thenReturn(Optional.of(employeeUser));

        assertThrows(UnauthorizedActionException.class, () -> userService.fireEmployee(fakeManager.getEmail(), employeeUser.getDni()));
    }

    @Test
    void inviteToWorkshop_ManagerHasNoWorkshop_ThrowsException() {
        User managerWithoutWorkshop = User.builder().dni("999").email("noworkshop@test.com").role(Role.MANAGER).build();
        when(userJpaRepository.findByEmail(managerWithoutWorkshop.getEmail())).thenReturn(Optional.of(managerWithoutWorkshop));

        assertThrows(UnauthorizedActionException.class, () ->
                userService.inviteToWorkshop(managerWithoutWorkshop.getEmail(), employeeUser.getDni(), Role.MECHANIC)
        );
    }

    @Test
    void inviteToWorkshop_SenderIsClient_ThrowsException() {
        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));

        assertThrows(UnauthorizedActionException.class, () ->
                userService.inviteToWorkshop(clientUser.getEmail(), employeeUser.getDni(), Role.MECHANIC)
        );
    }

    @Test
    void acceptInvitation_ManagerIsNull_AcceptsButDoesNotNotify() {
        clientUser.setPendingWorkshop(workshop);
        clientUser.setPendingRole(Role.MECHANIC);

        when(userJpaRepository.findByEmail(clientUser.getEmail())).thenReturn(Optional.of(clientUser));
        when(userJpaRepository.save(any(User.class))).thenReturn(clientUser);
        when(userJpaRepository.findFirstByWorkshopAndRole(workshop, Role.MANAGER)).thenReturn(Optional.empty());

        userService.acceptInvitation(clientUser.getEmail());

        verify(userJpaRepository).save(any(User.class));
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(NotificationDTO.class));
    }

    @Test
    void getEmployeesByWorkshopId_WorkshopNotFound_ThrowsException() {
        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));

        when(workshopJpaRepository.findById(workshop.getWorkshopId())).thenReturn(Optional.empty());

        assertThrows(WorkshopNotFoundException.class, () ->
                userService.getEmployeesByWorkshopId(workshop.getWorkshopId(), managerUser.getEmail())
        );
    }

    @Test
    void edit_UserToEditNotFound_ThrowsException() {
        NewUserDTO dto = new NewUserDTO("999", "Fantasma", "X", "X", null, null, null, null);

        when(userJpaRepository.findByEmail(managerUser.getEmail())).thenReturn(Optional.of(managerUser));
        when(userJpaRepository.findByDni("999")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.edit(dto, "999", managerUser.getEmail())
        );
    }
}