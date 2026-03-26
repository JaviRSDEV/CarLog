package com.carlog.backend.controller;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import com.carlog.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
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
@CrossOrigin(originPatterns = "[*]")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<NewVehicleDTO>> index(@RequestParam(required = false) Long workshopId, @RequestParam(required = false) String ownerId){
        if(workshopId != null){
            return ResponseEntity.ok(vehicleService.getByWorkshop(workshopId));
        }

        if(ownerId != null){
            return ResponseEntity.ok(vehicleService.getByOwner(ownerId));
        }
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{plate}")
    public ResponseEntity<NewVehicleDTO> showByPlate(@PathVariable String plate){
        return ResponseEntity.ok(vehicleService.getByPlate(plate));
    }

    @PostMapping
    public ResponseEntity<NewVehicleDTO> store(@RequestBody NewVehicleDTO vehicle){
        User connectedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.add(vehicle, connectedUser.getDni()));
    }

    @PutMapping("/{plate}")
    public NewVehicleDTO update(@RequestBody NewVehicleDTO vehicleData, @PathVariable String plate){
        return vehicleService.edit(vehicleData, plate);
    }

    @DeleteMapping("/{plate}")
    public NewVehicleDTO destroy(@PathVariable String plate){
        return vehicleService.delete(plate);
    }

    /*@PostMapping("/{plate}/entry")
    public ResponseEntity<NewVehicleDTO> registerEntry(@PathVariable String plate, @RequestParam Long workshopId){
        return ResponseEntity.ok(vehicleService.registerEntry(plate, workshopId));
    }*/

    @PostMapping("/{plate}/exit/{workshopId}")
    public ResponseEntity<NewVehicleDTO> registerExit(@PathVariable String plate, @PathVariable Long workshopId){
        return ResponseEntity.ok(vehicleService.registerExit(plate, workshopId));
    }

    @PutMapping("/{plate}/request-entry/{workshopId}")
    public ResponseEntity<NewVehicleDTO> requestEntry(@PathVariable String plate, @PathVariable Long workshopId){

        NewVehicleDTO updatedVehicle = vehicleService.requestEntry(plate, workshopId);
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
    public ResponseEntity<NewVehicleDTO> transferVehicle(@PathVariable String plate, @RequestParam String currentOwnerId, @RequestParam String newOwnerId){
        return ResponseEntity.ok(vehicleService.changeOwner(plate, currentOwnerId, newOwnerId));
    }
}
