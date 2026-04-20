package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.service.WorkOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByWorkshop(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.getWorkOrderByWorkshop(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByVehicle(@PathVariable String plate, Principal principal){
        return ResponseEntity.ok(workOrderService.getByVehicle(plate, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/mechanic/{dni}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByMechanic(@PathVariable String dni, Principal principal){
        return ResponseEntity.ok(workOrderService.getByEmployee(dni, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> show(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.getById(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PostMapping
    public ResponseEntity<NewWorkOrderResponseDTO> store(@Valid @RequestBody NewWorkOrderDTO workOrder, Principal principal){
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
    public ResponseEntity<NewWorkOrderResponseDTO> addLine(@Valid @PathVariable Long id, @RequestBody NewWorkOrderLineDTO line, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.addLine(id, line, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @DeleteMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> destroy(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.delete(id, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @DeleteMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> deleteLine(@PathVariable Long orderId, @PathVariable Long lineId, Principal principal){
        return ResponseEntity.ok(workOrderService.deleteLine(orderId, lineId, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PutMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> updateWorkOrderLine(@Valid @PathVariable Long orderId, @PathVariable Long lineId, @RequestBody NewWorkOrderLineDTO lineData, Principal principal){
        return ResponseEntity.ok(workOrderService.updateWorkOrderLine(orderId, lineId, lineData, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{orderId}/reassign")
    public ResponseEntity<NewWorkOrderResponseDTO> reassignWorkOrder(@PathVariable Long orderId, @RequestParam String newMechanicId, Principal principal){
        return ResponseEntity.ok(workOrderService.reassignMechanic(orderId, newMechanicId, principal.getName()));
    }
}