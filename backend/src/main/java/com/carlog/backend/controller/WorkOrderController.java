package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.service.InvoiceService;
import com.carlog.backend.service.WorkOrderService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
@Slf4j
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final InvoiceService invoiceService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByWorkshop(@PathVariable Long id, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.getWorkOrderByWorkshop(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<Page<NewWorkOrderResponseDTO>> getByVehicle(
            @PathVariable String plate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) Principal principal) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(workOrderService.getByVehicle(plate, principal.getName(), pageable));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/mechanic/{dni}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByMechanic(@PathVariable String dni, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.getByEmployee(dni, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> show(@PathVariable Long id, @Parameter(hidden = true)  Principal principal){
        return ResponseEntity.ok(workOrderService.getById(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PostMapping
    public ResponseEntity<NewWorkOrderResponseDTO> store(@Valid @RequestBody NewWorkOrderDTO workOrder, @Parameter(hidden = true) Principal principal){
        String userEmail = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.add(workOrder, userEmail, workOrder.vehiclePlate()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PutMapping("/{workOrderId}")
    public ResponseEntity<NewWorkOrderResponseDTO> update(@Valid @RequestBody UpdateWorkOrderDTO workOrderData, @PathVariable Long workOrderId, Principal principal){
        return ResponseEntity.ok(workOrderService.edit(workOrderData, workOrderId, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PostMapping("/{id}/lines")
    public ResponseEntity<NewWorkOrderResponseDTO> addLine(@Valid @PathVariable Long id, @RequestBody NewWorkOrderLineDTO line, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.addLine(id, line, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @DeleteMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> destroy(@PathVariable Long id, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.delete(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @DeleteMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> deleteLine(@PathVariable Long orderId, @PathVariable Long lineId, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.deleteLine(orderId, lineId, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PutMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> updateWorkOrderLine(@Valid @PathVariable Long orderId, @PathVariable Long lineId, @RequestBody NewWorkOrderLineDTO lineData, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.updateWorkOrderLine(orderId, lineId, lineData, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{orderId}/reassign")
    public ResponseEntity<NewWorkOrderResponseDTO> reassignWorkOrder(@PathVariable Long orderId, @RequestParam String newMechanicId, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(workOrderService.reassignMechanic(orderId, newMechanicId, principal.getName()));
    }

    @PostMapping("/{id}/notify-pickup")
    public ResponseEntity<Map<String, String>> notifyPickup(
            @PathVariable Long id,
            Principal principal
    ){
        workOrderService.notifyClientForPickup(id, principal.getName());
        return ResponseEntity.ok(Map.of("message", "Notificación enviada con éxito al ciente"));
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id){
        try{
            byte[] pdfBytes = invoiceService.generateInvoicePdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "factura_carlog_" + id + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}