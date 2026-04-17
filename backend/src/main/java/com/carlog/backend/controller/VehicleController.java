package com.carlog.backend.controller;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkOrderResponseDTO;
import com.carlog.backend.model.User;
import com.carlog.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<NewVehicleDTO>> index(@RequestParam(required = false) Long workshopId, @RequestParam(required = false) String ownerId, Principal principal){
        if(workshopId != null){
            return ResponseEntity.ok(vehicleService.getByWorkshop(workshopId, principal.getName()));
        }

        if(ownerId != null){
            return ResponseEntity.ok(vehicleService.getByOwner(ownerId, principal.getName()));
        }
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{plate}")
    public ResponseEntity<NewVehicleDTO> showByPlate(@PathVariable String plate, Principal principal){
        return ResponseEntity.ok(vehicleService.getByPlate(plate, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<NewVehicleDTO> store(@RequestBody NewVehicleDTO vehicle){
        User connectedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.add(vehicle, connectedUser.getDni()));
    }

    @PutMapping("/{plate}")
    public NewVehicleDTO update(@RequestBody NewVehicleDTO vehicleData, @PathVariable String plate, Principal principal){
        return vehicleService.edit(vehicleData, plate, principal.getName());
    }

    @DeleteMapping("/{plate}")
    public NewVehicleDTO destroy(@PathVariable String plate, Principal principal){
        return vehicleService.delete(plate, principal.getName());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PostMapping("/{plate}/exit/{workshopId}")
    public ResponseEntity<NewVehicleDTO> registerExit(@PathVariable String plate, @PathVariable Long workshopId, Principal principal){
        return ResponseEntity.ok(vehicleService.registerExit(plate, workshopId, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @PutMapping("/{plate}/request-entry/{workshopId}")
    public ResponseEntity<NewVehicleDTO> requestEntry(@PathVariable String plate, @PathVariable Long workshopId, Principal principal){
        NewVehicleDTO updatedVehicle = vehicleService.requestEntry(plate, workshopId, principal.getName());
        return ResponseEntity.ok(updatedVehicle);
    }

    @PutMapping("/{plate}/approve-entry")
    public ResponseEntity<NewVehicleDTO> approveEntry(@PathVariable String plate, Principal principal){
        String userDni = principal.getName();
        NewVehicleDTO updatedVehicle = vehicleService.approveEntry(plate, userDni);
        return ResponseEntity.ok(updatedVehicle);
    }

    @PutMapping("/{plate}/reject-entry")
    public ResponseEntity<NewVehicleDTO> rejectEntry(@PathVariable String plate, Principal principal){
        String userDni = principal.getName();
        System.out.println(userDni);
        NewVehicleDTO updatedVehicle = vehicleService.rejectEntry(plate, userDni);
        return ResponseEntity.ok(updatedVehicle);
    }

    @PostMapping("/{plate}/transfer")
    public ResponseEntity<NewVehicleDTO> transferVehicle(@PathVariable String plate, @RequestParam String currentOwnerId, @RequestParam String newOwnerId, Principal principal){
        return ResponseEntity.ok(vehicleService.changeOwner(plate, currentOwnerId, newOwnerId, principal.getName()));
    }

    @GetMapping("/{plate}/history")
    public ResponseEntity<List<NewWorkOrderResponseDTO>> getVehicleHistory(@PathVariable String plate, Principal principal){
        return ResponseEntity.ok(vehicleService.getVehicleHistory(plate, principal.getName()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewVehicleDTO>> search(@RequestParam String q, @RequestParam Long workshopId, @RequestParam String type, Principal principal) {

        return ResponseEntity.ok(vehicleService.searchVehicles(q, workshopId, type, principal.getName()));
    }
}
