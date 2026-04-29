package com.carlog.backend.controller;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.error.GlobalExceptionHandler;
import com.carlog.backend.error.UnauthorizedActionException;
import com.carlog.backend.error.VehicleNotFoundException;
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
                .setControllerAdvice(new GlobalExceptionHandler()) // ¡Activado!
                .build();

        mockPrincipal = () -> "mechanic@test.com";

        mockVehicle = new NewVehicleDTO(
                "1234ABC", "Toyota", "Corolla", 120000L, "2.0 Hybrid", 184, 190,
                "225/45 R17", new ArrayList<>(), LocalDate.now(), 1L, "11111111A", null, null
        );
    }

    @Test
    @DisplayName("GET /api/vehicles - Branching: Workshop filter")
    void index_WorkshopFilter() throws Exception {
        Page<NewVehicleDTO> page = new PageImpl<>(List.of(mockVehicle));
        when(vehicleService.getByWorkshop(eq(1L), anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles")
                        .param("workshopId", "1")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].plate").value("1234ABC"));

        verify(vehicleService).getByWorkshop(eq(1L), anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/vehicles/{plate} - Not Found Case")
    void showByPlate_NotFound() throws Exception {
        when(vehicleService.getByPlate(eq("404-NOTFOUND"), anyString()))
                .thenThrow(new VehicleNotFoundException("404-NOTFOUND"));

        mockMvc.perform(get("/api/vehicles/404-NOTFOUND").principal(mockPrincipal))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No hay un vehículo con con matricula 404-NOTFOUND"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @DisplayName("POST /api/vehicles - Validation Error (Empty Plate)")
    void store_ValidationError() throws Exception {
        NewVehicleDTO invalidVehicle = new NewVehicleDTO(
                "", "", "", -1L, null, -5, -5, null, null, null, null, null, null, null
        );

        mockMvc.perform(post("/api/vehicles")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidVehicle)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("PUT /api/vehicles/{plate}/request-entry/{workshopId} - Unauthorized Intruder")
    void requestEntry_Unauthorized() throws Exception {
        when(vehicleService.requestEntry(anyString(), anyLong(), anyString()))
                .thenThrow(new UnauthorizedActionException("No perteneces a este taller"));

        mockMvc.perform(put("/api/vehicles/1234ABC/request-entry/99")
                        .principal(mockPrincipal))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("No perteneces a este taller"))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    @DisplayName("POST /transfer - Happy Path")
    void transferVehicle_Success() throws Exception {
        when(vehicleService.changeOwner(eq("1234ABC"), eq("99999999Z"), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(post("/api/vehicles/1234ABC/transfer")
                        .param("newOwnerId", "99999999Z")
                        .principal(mockPrincipal))
                .andExpect(status().isOk());

        verify(vehicleService).changeOwner(eq("1234ABC"), eq("99999999Z"), anyString());
    }

    @Test
    @DisplayName("GET /search - Verify Parameters")
    void search_Success() throws Exception {
        Page<NewVehicleDTO> searchPage = new PageImpl<>(List.of(mockVehicle));
        when(vehicleService.searchVehicles(eq("Toyota"), eq(1L), eq("BRAND"), anyString(), any(Pageable.class)))
                .thenReturn(searchPage);

        mockMvc.perform(get("/api/vehicles/search")
                        .param("q", "Toyota")
                        .param("workshopId", "1")
                        .param("type", "BRAND")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Toyota"));
    }

    @Test
    @DisplayName("DELETE /api/vehicles/{plate} - Happy Path")
    void destroy_Success() throws Exception {
        when(vehicleService.delete(eq("1234ABC"), anyString())).thenReturn(mockVehicle);

        mockMvc.perform(delete("/api/vehicles/1234ABC")
                        .principal(mockPrincipal))
                .andExpect(status().isOk());
    }
}