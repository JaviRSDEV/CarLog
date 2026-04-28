package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.error.WorkOrderNotFoundException;
import com.carlog.backend.service.WorkOrderService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter; // El que te funciona seguro
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WorkOrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkOrderService workOrderService;

    @InjectMocks
    private WorkOrderController workOrderController;

    private ObjectMapper objectMapper;
    private Principal mockPrincipal;

    @JsonIgnoreProperties({"pageable", "sort", "first", "last", "numberOfElements", "empty"})
    private abstract static class PageMixin {}

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        objectMapper.addMixIn(Page.class, PageMixin.class);
        objectMapper.addMixIn(PageImpl.class, PageMixin.class);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(workOrderController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(converter)
                .build();

        mockPrincipal = () -> "empleado@taller.com";
    }

    @Test
    @DisplayName("GET /workshop/{id} - Happy Path")
    void showByWorkshop_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.getWorkOrderByWorkshop(eq(1L), anyString())).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/api/workorders/workshop/1").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /vehicle/{plate} - Happy Path (Devuelve Page)")
    void getByVehicle_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        Page<NewWorkOrderResponseDTO> page = new PageImpl<>(List.of(mockResponse));

        when(workOrderService.getByVehicle(eq("1234ABC"), anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/workorders/vehicle/1234ABC")
                        .param("page", "0")
                        .param("size", "10")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("GET /mechanic/{dni} - Happy Path")
    void showByMechanic_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.getByEmployee(eq("12345678A"), anyString())).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/api/workorders/mechanic/12345678A").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET /{id} - Happy Path")
    void show_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.getById(eq(1L), anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/api/workorders/1").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST / - Happy Path")
    void store_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.add(any(NewWorkOrderDTO.class), anyString(), any())).thenReturn(mockResponse);

        String validJsonRequest = "{}";

        mockMvc.perform(post("/api/workorders")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /{workOrderId} - Happy Path")
    void update_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.edit(any(UpdateWorkOrderDTO.class), eq(1L), anyString())).thenReturn(mockResponse);

        String validJsonRequest = "{}";

        mockMvc.perform(put("/api/workorders/1")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /{id}/lines - Happy Path")
    void addLine_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.addLine(eq(1L), any(NewWorkOrderLineDTO.class), anyString())).thenReturn(mockResponse);

        String validJsonRequest = "{}";

        mockMvc.perform(post("/api/workorders/1/lines")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /{orderId}/lines/{lineId} - Happy Path")
    void updateWorkOrderLine_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.updateWorkOrderLine(eq(1L), eq(2L), any(NewWorkOrderLineDTO.class), anyString()))
                .thenReturn(mockResponse);

        String validJsonRequest = "{}";

        mockMvc.perform(put("/api/workorders/1/lines/2")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /{orderId}/reassign - Happy Path")
    void reassignWorkOrder_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.reassignMechanic(eq(1L), eq("87654321B"), anyString())).thenReturn(mockResponse);

        mockMvc.perform(patch("/api/workorders/1/reassign")
                        .param("newMechanicId", "87654321B")
                        .principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /{id} - Happy Path")
    void destroy_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.delete(eq(1L), anyString())).thenReturn(mockResponse);

        mockMvc.perform(delete("/api/workorders/1").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /{orderId}/lines/{lineId} - Happy Path")
    void deleteLine_Success() throws Exception {
        NewWorkOrderResponseDTO mockResponse = mock(NewWorkOrderResponseDTO.class);
        when(workOrderService.deleteLine(eq(1L), eq(2L), anyString())).thenReturn(mockResponse);

        mockMvc.perform(delete("/api/workorders/1/lines/2").principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /{id} - Throws WorkOrderNotFoundException")
    void show_ThrowsWorkOrderNotFoundException() {
        when(workOrderService.getById(eq(99L), anyString()))
                .thenThrow(new WorkOrderNotFoundException());

        assertThatThrownBy(() -> mockMvc.perform(get("/api/workorders/99").principal(mockPrincipal)))
                .hasCauseInstanceOf(WorkOrderNotFoundException.class)
                .hasMessageContaining("No hay ordenes de trabajo con esos requisitos de búsqueda");
    }
}