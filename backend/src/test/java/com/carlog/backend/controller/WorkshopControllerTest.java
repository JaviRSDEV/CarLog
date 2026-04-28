package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.service.UserService;
import com.carlog.backend.service.WorkshopService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WorkshopControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkshopService workshopService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WorkshopController workshopController;

    private ObjectMapper objectMapper;
    private Principal mockPrincipal;
    private NewWorkshopDTO mockWorkshop;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(workshopController)
                .setMessageConverters(converter)
                .build();

        mockPrincipal = () -> "manager@taller.com";

        mockWorkshop = new NewWorkshopDTO(
                1L,
                "Taller Paco",
                "Calle Falsa 123",
                "600123456",
                "contacto@tallerpaco.com",
                "https://cloudinary.com/mifoto.png"
        );
    }

    @Test
    @DisplayName("GET /details/{id} - Happy Path")
    void showById_Success() throws Exception {
        when(workshopService.getWorkshopById(eq(1L), anyString())).thenReturn(mockWorkshop);

        mockMvc.perform(get("/api/workshop/details/1").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workshopName").value("Taller Paco"))
                .andExpect(jsonPath("$.workshopPhone").value("600123456"));
    }

    @Test
    @DisplayName("GET /{id}/employees - Happy Path")
    void showEmployees_Success() throws Exception {
        NewUserDTO mockUser = mock(NewUserDTO.class);
        when(userService.getEmployeesByWorkshopId(eq(1L), anyString())).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/api/workshop/1/employees").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("POST /create - Happy Path")
    void store_Success() throws Exception {
        when(workshopService.add(any(NewWorkshopDTO.class), anyString())).thenReturn(mockWorkshop);

        mockMvc.perform(post("/api/workshop/create")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockWorkshop)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.workshopEmail").value("contacto@tallerpaco.com"));
    }

    @Test
    @DisplayName("PUT /details/{id} (Multipart) - Happy Path")
    void update_Multipart_Success() throws Exception {
        MockMultipartFile workshopDataPart = new MockMultipartFile(
                "workshopData",
                "",
                "application/json",
                objectMapper.writeValueAsBytes(mockWorkshop)
        );

        MockMultipartFile filePart = new MockMultipartFile(
                "file",
                "logo.png",
                "image/png",
                "dummy-image-bytes".getBytes()
        );

        when(workshopService.edit(any(NewWorkshopDTO.class), eq(1L), any(), anyString()))
                .thenReturn(mockWorkshop);
        MockMultipartHttpServletRequestBuilder builder =
                (MockMultipartHttpServletRequestBuilder) multipart("/api/workshop/details/1")
                        .with(request -> {
                            request.setMethod(HttpMethod.PUT.name());
                            return request;
                        });

        mockMvc.perform(builder
                        .file(workshopDataPart)
                        .file(filePart)
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workshopName").value("Taller Paco"));
    }

    @Test
    @DisplayName("DELETE /details/{id} - Happy Path")
    void destroy_Success() throws Exception {
        when(workshopService.delete(eq(1L), anyString())).thenReturn(mockWorkshop);

        mockMvc.perform(delete("/api/workshop/details/1").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workshopId").value(1));
    }

    @Test
    @DisplayName("GET /details/{id} - Throws WorkshopNotFoundException")
    void showById_ThrowsWorkshopNotFoundException() {
        when(workshopService.getWorkshopById(eq(99L), anyString()))
                .thenThrow(new WorkshopNotFoundException("Taller no encontrado"));

        assertThatThrownBy(() -> mockMvc.perform(get("/api/workshop/details/99").principal(mockPrincipal)))
                .hasCauseInstanceOf(WorkshopNotFoundException.class)
                .hasMessageContaining("Taller no encontrado");
    }
}