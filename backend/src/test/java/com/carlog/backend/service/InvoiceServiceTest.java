package com.carlog.backend.service;

import com.carlog.backend.error.WorkOrderNotFoundException;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private WorkOrderJpaRepository workOrderJpaRepository;

    @Mock
    private SpringTemplateEngine springTemplateEngine;

    @InjectMocks
    private InvoiceService invoiceService;

    private WorkOrder mockWorkOrder;

    @BeforeEach
    void setUp() {
        User client = User.builder().dni("11111111A").name("Cliente Test").build();
        Workshop workshop = Workshop.builder().workshopId(1L).workshopName("Taller Test").build();

        Vehicle vehicle = Vehicle.builder().plate("1234ABC").owner(client).workshop(workshop).build();

        mockWorkOrder = new WorkOrder();
        mockWorkOrder.setId(100L);
        mockWorkOrder.setVehicle(vehicle);
        mockWorkOrder.setWorkshop(workshop);
        mockWorkOrder.setTotalAmount(108.90);
        mockWorkOrder.setLines(new ArrayList<>());

        WorkOrderLine line = new WorkOrderLine();
        line.setQuantity(2.0);
        line.setPricePerUnit(50.0);
        line.setIva(21.0);
        line.setDiscount(10.0);
        mockWorkOrder.addWorkOrderLine(line);
    }

    @Test
    @DisplayName("Generar Factura - Happy Path (Devuelve Array de Bytes)")
    void generateInvoicePdf_Success() throws Exception {
        when(workOrderJpaRepository.findById(100L)).thenReturn(Optional.of(mockWorkOrder));

        String validHtmlMock = "<html><body><p>Factura</p></body></html>";
        when(springTemplateEngine.process(eq("invoice-template"), any(Context.class)))
                .thenReturn(validHtmlMock);

        byte[] pdfBytes = invoiceService.generateInvoicePdf(100L);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(springTemplateEngine).process(eq("invoice-template"), contextCaptor.capture());

        Context capturedContext = contextCaptor.getValue();

        assertEquals(mockWorkOrder, capturedContext.getVariable("order"));
        assertEquals("11111111A", ((User) capturedContext.getVariable("client")).getDni());

        assertEquals(100.0, capturedContext.getVariable("imponibleBase"));
        assertEquals(21.0, capturedContext.getVariable("totalIva"));
        assertEquals(12.1, capturedContext.getVariable("totalDiscount"));
    }

    @Test
    @DisplayName("Generar Factura - Orden No Encontrada")
    void generateInvoicePdf_ThrowsException_WhenOrderNotFound() {
        when(workOrderJpaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(WorkOrderNotFoundException.class, () ->
                invoiceService.generateInvoicePdf(999L)
        );

        verify(springTemplateEngine, never()).process(anyString(), any(Context.class));
    }
}