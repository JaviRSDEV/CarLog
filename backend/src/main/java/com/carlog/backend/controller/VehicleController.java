package com.carlog.backend.controller;

import com.carlog.backend.DTO.NewVehicleDTO;
import com.carlog.backend.model.Vehicle;
import com.carlog.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> index(@RequestParam(required = false) Long workshopId){
        if(workshopId != null){
            return ResponseEntity.ok(vehicleService.getByWorkshop(workshopId));
        }
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{plate}")
    public ResponseEntity<Vehicle> showByPlate(@PathVariable String plate){
        return ResponseEntity.ok(vehicleService.getByPlate(plate));
    }

    @PostMapping
    public ResponseEntity<NewVehicleDTO> store(@RequestBody NewVehicleDTO vehicle, @RequestHeader("User-Dni") String connectedUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.add(vehicle, connectedUser));
    }

    @PutMapping("/{plate}")
    public NewVehicleDTO update(@RequestBody NewVehicleDTO vehicleData, @PathVariable String plate){
        return vehicleService.edit(vehicleData, plate);
    }

    @DeleteMapping("/{plate}")
    public NewVehicleDTO destroy(@PathVariable String plate){
        return vehicleService.delete(plate);
    }

    @PostMapping("/{plate}/entry")
    public ResponseEntity<NewVehicleDTO> registerEntry(@PathVariable String plate, @RequestParam Long workshopId){
        return ResponseEntity.ok(vehicleService.registerEntry(plate, workshopId));
    }

    @PostMapping("/{plate}/exit")
    public ResponseEntity<NewVehicleDTO> registerExit(@PathVariable String plate, @RequestParam Long workshopId){
        return ResponseEntity.ok(vehicleService.registerExit(plate, workshopId));
    }

    @PostMapping("/{plate}/transfer")
    public ResponseEntity<NewVehicleDTO> transferVehicle(@PathVariable String plate, @RequestParam String currentOwnerId, @RequestParam String newOwnerId){
        return ResponseEntity.ok(vehicleService.changeOwner(plate, currentOwnerId, newOwnerId));
    }
}
