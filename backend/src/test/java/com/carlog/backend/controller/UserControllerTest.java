package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.model.Role;
import com.carlog.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;
    private NewUserDTO mockUserDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        mockUserDTO = new NewUserDTO(
                "12345678A",
                "Carlos Gómez",
                "carlos@example.com",
                "600123456",
                Role.MECHANIC,
                1L,
                null,
                null
        );
    }

    @Test
    @DisplayName("GET /me - Retorna el perfil del usuario autenticado")
    void show_Success() throws Exception {
        when(userService.getMyProfile()).thenReturn(mockUserDTO);

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678A"))
                .andExpect(jsonPath("$.role").value("MECHANIC"))
                .andExpect(jsonPath("$.workShopId").value(1));
    }

    @Test
    @DisplayName("GET /search/{name} - Retorna lista cuando hay coincidencias")
    void searchByName_Found() throws Exception {
        when(userService.getByName("Carlos")).thenReturn(List.of(mockUserDTO));

        mockMvc.perform(get("/api/users/search/Carlos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Carlos Gómez"));
    }

    @Test
    @DisplayName("GET /search/{name} - Retorna lista vacía (Happy Path sin resultados)")
    void searchByName_Empty() throws Exception {
        when(userService.getByName("Inexistente")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users/search/Inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("POST /api/users - Crea un usuario correctamente")
    void store_Success() throws Exception {
        when(userService.add(any(NewUserDTO.class))).thenReturn(mockUserDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dni").value("12345678A"));
    }

    @Test
    @DisplayName("PUT /{dni} - Actualiza datos del usuario")
    void update_Success() throws Exception {
        when(userService.edit(any(NewUserDTO.class), eq("12345678A"), eq("carlos@example.com")))
                .thenReturn(mockUserDTO);

        Principal mockPrincipal = () -> "carlos@example.com";

        mockMvc.perform(put("/api/users/12345678A")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carlos Gómez"));
    }

    @Test
    @DisplayName("PATCH /{dni}/invite - Envía invitación al taller")
    void invite_Success() throws Exception {
        Principal mockPrincipal = () -> "manager@test.com";

        mockMvc.perform(patch("/api/users/12345678A/invite")
                        .principal(mockPrincipal)
                        .param("managerDni", "87654321B")
                        .param("newRole", "MECHANIC"))
                .andExpect(status().isOk());

        verify(userService).inviteToWorkshop("manager@test.com", "12345678A", Role.MECHANIC);
    }

    @Test
    @DisplayName("PATCH /accept - Acepta invitación pendiente")
    void accept_Success() throws Exception {
        when(userService.acceptInvitation("user@test.com")).thenReturn(mockUserDTO);
        Principal mockPrincipal = () -> "user@test.com";

        mockMvc.perform(patch("/api/users/accept").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678A"));
    }

    @Test
    @DisplayName("PATCH /reject - Rechaza invitación pendiente")
    void reject_Success() throws Exception {
        when(userService.rejectInvitation("user@test.com")).thenReturn(mockUserDTO);
        Principal mockPrincipal = () -> "user@test.com";

        mockMvc.perform(patch("/api/users/reject").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /{dni}/fire - Despide a un empleado")
    void fireEmployee_Success() throws Exception {
        Principal mockPrincipal = () -> "manager@test.com";

        mockMvc.perform(patch("/api/users/12345678A/fire").principal(mockPrincipal))
                .andExpect(status().isOk());

        verify(userService).fireEmployee("manager@test.com", "12345678A");
    }
}