package com.carlog.backend.controller;

import com.carlog.backend.dto.*;
import com.carlog.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDTO> getDashboardStats(){
        return ResponseEntity.ok(adminService.getGlobalStats());
    }

    @GetMapping("/users")
    public ResponseEntity<Page<NewUserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("dni").ascending());
        return ResponseEntity.ok(adminService.getAllUsers(pageable));
    }

    @GetMapping("/workshops")
    public ResponseEntity<Page<NewWorkshopDTO>> getAllWorkshops(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("workshopId").descending());
        return ResponseEntity.ok(adminService.getAllWorkshops(pageable));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<Page<NewVehicleDTO>> getAllVehicles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("plate").ascending());
        return ResponseEntity.ok(adminService.getAllVehicles(pageable));
    }

    @GetMapping("/workorders")
    public ResponseEntity<Page<NewWorkOrderResponseDTO>> getAllWorkorders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(adminService.getAllWorkOrders(pageable));
    }

    @GetMapping("/catalog/brands")
    public ResponseEntity<Page<CarBrandDTO>> getAllBrands(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(adminService.getAllCarBrands(pageable));
    }

    @GetMapping("/catalog/models")
    public ResponseEntity<Page<CarModelDTO>> getAllModels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(adminService.getAllCarModels(pageable));
    }

    @GetMapping("/catalog/versions")
    public ResponseEntity<Page<CarVersionDTO>> getAllVersions(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("versionName").ascending());
        return ResponseEntity.ok(adminService.getAllCarVersions(pageable));
    }
 }
