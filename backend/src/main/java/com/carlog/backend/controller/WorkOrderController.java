package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping
    public ResponseEntity<List<NewWorkOrderResponseDTO>> index(@RequestParam(required = false) String mechanicDni, Principal principal){
        if(mechanicDni != null)
            return ResponseEntity.ok(workOrderService.getByEmployee(mechanicDni, principal.getName()));

        return ResponseEntity.ok(workOrderService.getAll());
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByWorkshop(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.getWorkOrderByWorkshop(id, principal.getName()));
    }

    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByVehicle(@PathVariable String plate, Principal principal){
        return ResponseEntity.ok(workOrderService.getByVehicle(plate, principal.getName()));
    }

    @GetMapping("/mechanic/{dni}")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> showByMechanic(@PathVariable String dni, Principal principal){
        return ResponseEntity.ok(workOrderService.getByEmployee(dni, principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> show(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.getById(id, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<NewWorkOrderResponseDTO> store(@RequestBody NewWorkOrderDTO workOrder, Principal principal){
        String userEmail = principal.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.add(workOrder, userEmail, workOrder.vehiclePlate()));
    }

    @PutMapping("/{workOrderId}")
    public ResponseEntity<NewWorkOrderResponseDTO> update(@RequestBody UpdateWorkOrderDTO workOrderData, @PathVariable Long workOrderId, Principal principal){
        return ResponseEntity.ok(workOrderService.edit(workOrderData, workOrderId, principal.getName()));
    }

    @PostMapping("/{id}/lines")
    public ResponseEntity<NewWorkOrderResponseDTO> addLine(@PathVariable Long id, @RequestBody NewWorkOrderLineDTO line, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.addLine(id, line, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> destroy(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(workOrderService.delete(id, principal.getName()));
    }

    @DeleteMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> deleteLine(@PathVariable Long orderId, @PathVariable Long lineId, Principal principal){
        return ResponseEntity.ok(workOrderService.deleteLine(orderId, lineId, principal.getName()));
    }

    @PutMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<NewWorkOrderResponseDTO> updateWorkOrderLine(@PathVariable Long orderId, @PathVariable Long lineId, @RequestBody NewWorkOrderLineDTO lineData, Principal principal){
        return ResponseEntity.ok(workOrderService.updateWorkOrderLine(orderId, lineId, lineData, principal.getName()));
    }

    @PatchMapping("/{orderId}/reassign")
    public ResponseEntity<NewWorkOrderResponseDTO> reassignWorkOrder(@PathVariable Long orderId, @RequestParam String newMechanicId, Principal principal){
        return ResponseEntity.ok(workOrderService.reassignMechanic(orderId, newMechanicId, principal.getName()));
    }
}