package com.carlog.backend.controller;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.service.VehicleService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private ObjectMapper objectMapper;
    private NewVehicleDTO mockVehicle;
    private Principal mockPrincipal;

    @JsonIgnoreProperties({"pageable", "sort"})
    private abstract static class PageMixin {}

    @BeforeEach
    void setUp() {

        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        objectMapper.addMixIn(Page.class, PageMixin.class);
        objectMapper.addMixIn(PageImpl.class, PageMixin.class);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(converter)
                .build();

        mockPrincipal = () -> "12345678A";

        mockVehicle = new NewVehicleDTO(
                "1234ABC", "Toyota", "Corolla", 120000L, "2.0 Hybrid", 184, 190,
                "225/45 R17", new ArrayList<>(), LocalDate.now(), 1L, "12345678A", null, null
        );
    }

    @Test
    @DisplayName("GET /api/vehicles - Happy Path (Varios filtros)")
    void index_Success() throws Exception {
        Page<NewVehicleDTO> page = new PageImpl<>(List.of(mockVehicle));

        when(vehicleService.getMyVehicles(anyString(), any(Pageable.class))).thenReturn(page);
        when(vehicleService.getByWorkshop(anyLong(), anyString(), any(Pageable.class))).thenReturn(page);
        when(vehicleService.getByOwner(anyString(), anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles").param("workshopId", "1").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].plate").value("1234ABC"));

        mockMvc.perform(get("/api/vehicles").param("ownerId", "owner123").principal(mockPrincipal))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/vehicles").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/vehicles/{plate} - Happy Path")
    void showByPlate_Success() throws Exception {
        when(vehicleService.getByPlate(eq("1234ABC"), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(get("/api/vehicles/1234ABC").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    @DisplayName("POST /api/vehicles - Happy Path")
    void store_Success() throws Exception {
        when(vehicleService.add(any(NewVehicleDTO.class), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(post("/api/vehicles")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockVehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.plate").value("1234ABC"));
    }

    @Test
    @DisplayName("PUT /api/vehicles/{plate} - Happy Path")
    void update_Success() throws Exception {
        when(vehicleService.edit(any(NewVehicleDTO.class), eq("1234ABC"), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(put("/api/vehicles/1234ABC")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockVehicle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.engine").value("2.0 Hybrid"));
    }

    @Test
    @DisplayName("DELETE /api/vehicles/{plate} - Happy Path")
    void destroy_Success() throws Exception {
        when(vehicleService.delete(eq("1234ABC"), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(delete("/api/vehicles/1234ABC").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /{plate}/exit/{workshopId} - Happy Path")
    void registerExit_Success() throws Exception {
        when(vehicleService.registerExit(anyString(), anyLong(), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(post("/api/vehicles/1234ABC/exit/1").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /{plate}/request-entry/{workshopId} - Happy Path")
    void requestEntry_Success() throws Exception {
        when(vehicleService.requestEntry(anyString(), anyLong(), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(put("/api/vehicles/1234ABC/request-entry/1").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /approve-entry - Happy Path")
    void approveEntry_Success() throws Exception {
        when(vehicleService.approveEntry(anyString(), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(put("/api/vehicles/1234ABC/approve-entry").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /reject-entry - Happy Path")
    void rejectEntry_Success() throws Exception {
        when(vehicleService.rejectEntry(anyString(), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(put("/api/vehicles/1234ABC/reject-entry").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /transfer - Happy Path")
    void transferVehicle_Success() throws Exception {
        when(vehicleService.changeOwner(anyString(), anyString(), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(post("/api/vehicles/1234ABC/transfer")
                        .param("newOwnerId", "99999999Z")
                        .principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /{plate}/history - Happy Path")
    void getVehicleHistory_Success() throws Exception {
        Page<NewWorkOrderResponseDTO> historyPage = new PageImpl<>(List.of());
        when(vehicleService.getVehicleHistory(anyString(), anyString(), any(Pageable.class))).thenReturn(historyPage);

        mockMvc.perform(get("/api/vehicles/1234ABC/history").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /search - Happy Path")
    void search_Success() throws Exception {
        Page<NewVehicleDTO> searchPage = new PageImpl<>(List.of(mockVehicle));
        when(vehicleService.searchVehicles(anyString(), anyLong(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(searchPage);

        mockMvc.perform(get("/api/vehicles/search")
                        .param("q", "Toyota")
                        .param("workshopId", "1")
                        .param("type", "BRAND")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].kilometers").value(120000));
    }
}