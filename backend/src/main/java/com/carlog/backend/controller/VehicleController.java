package com.carlog.backend.controller;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.service.VehicleService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<Page<NewVehicleDTO>> index(
            @RequestParam(required = false) Long workshopId,
            @RequestParam(required = false) String ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) Principal principal){

        Pageable pageable = PageRequest.of(page, size);

        if(workshopId != null){
            return ResponseEntity.ok(vehicleService.getByWorkshop(workshopId, principal.getName(), pageable));
        }
        if(ownerId != null){
            return ResponseEntity.ok(vehicleService.getByOwner(ownerId, principal.getName(), pageable));
        }
        return ResponseEntity.ok(vehicleService.getMyVehicles(principal.getName(), pageable));
    }

    @GetMapping("/{plate}")
    public ResponseEntity<NewVehicleDTO> showByPlate(@PathVariable String plate, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.getByPlate(plate, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<NewVehicleDTO> store(@Valid @RequestBody NewVehicleDTO vehicle, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.add(vehicle, principal.getName()));
    }

    @PutMapping("/{plate}")
    public NewVehicleDTO update(@Valid @RequestBody NewVehicleDTO vehicleData, @PathVariable String plate, @Parameter(hidden = true) Principal principal){
        return vehicleService.edit(vehicleData, plate, principal.getName());
    }

    @DeleteMapping("/{plate}")
    public NewVehicleDTO destroy(@PathVariable String plate, @Parameter(hidden = true) Principal principal){
        return vehicleService.delete(plate, principal.getName());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PostMapping("/{plate}/exit/{workshopId}")
    public ResponseEntity<NewVehicleDTO> registerExit(@PathVariable String plate, @PathVariable Long workshopId, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.registerExit(plate, workshopId, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PutMapping("/{plate}/request-entry/{workshopId}")
    public ResponseEntity<NewVehicleDTO> requestEntry(@PathVariable String plate, @PathVariable Long workshopId, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.requestEntry(plate, workshopId, principal.getName()));
    }

    @PutMapping("/{plate}/approve-entry")
    public ResponseEntity<NewVehicleDTO> approveEntry(@PathVariable String plate, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.approveEntry(plate, principal.getName()));
    }

    @PutMapping("/{plate}/reject-entry")
    public ResponseEntity<NewVehicleDTO> rejectEntry(@PathVariable String plate, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.rejectEntry(plate, principal.getName()));
    }

    @PostMapping("/{plate}/transfer")
    public ResponseEntity<NewVehicleDTO> transferVehicle(@PathVariable String plate, @RequestParam String newOwnerId, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(vehicleService.changeOwner(plate, newOwnerId, principal.getName()));
    }

    @GetMapping("/{plate}/history")
    public ResponseEntity<Page<NewWorkOrderResponseDTO>> getVehicleHistory(
            @PathVariable String plate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) Principal principal){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.getVehicleHistory(plate, principal.getName(), pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NewVehicleDTO>> search(
            @RequestParam String q,
            @RequestParam Long workshopId,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(hidden = true) Principal principal) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.searchVehicles(q, workshopId, type, principal.getName(), pageable));
    }
}