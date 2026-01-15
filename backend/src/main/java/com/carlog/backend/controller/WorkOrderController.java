package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.dto.NewWorkOrderLineDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.dto.UpdateWorkOrderDTO;
import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping
    public ResponseEntity<List<WorkOrder>> index(@RequestParam(required = false) String mechanicDni){
        if(mechanicDni != null)
            return ResponseEntity.ok(workOrderService.getByEmployee(mechanicDni));

        return ResponseEntity.ok(workOrderService.getAll());
    }

    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<List<WorkOrder>> showByVehicle(@PathVariable String plate){
        return ResponseEntity.ok(workOrderService.getByVehicle(plate));
    }

    @GetMapping("/mechanic/{Dni}")
    public ResponseEntity<List<WorkOrder>> showByMechanic(@PathVariable String dni){
        return ResponseEntity.ok(workOrderService.getByEmployee(dni));
    }

    @PostMapping
    public ResponseEntity<NewWorkOrderResponseDTO> store(@RequestBody NewWorkOrderDTO workOrder, @RequestHeader("User-Dni") String connectedUser, @RequestHeader("Vehicle-plate") String plate){
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.add(workOrder, connectedUser, plate));
    }

    @PutMapping("/{workOrderId}")
    public ResponseEntity<NewWorkOrderResponseDTO> update(@RequestBody UpdateWorkOrderDTO workOrderData, @PathVariable Long workOrderId){
        return ResponseEntity.ok(workOrderService.edit(workOrderData, workOrderId));
    }

    @PostMapping("/{id}/lines")
    public ResponseEntity<NewWorkOrderResponseDTO> addLine(@PathVariable Long id, @RequestBody NewWorkOrderLineDTO line){
        return ResponseEntity.status(HttpStatus.CREATED).body(workOrderService.addLine(id, line));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewWorkOrderResponseDTO> destroy(@PathVariable Long id){
        return ResponseEntity.ok(workOrderService.delete(id));
    }

}
