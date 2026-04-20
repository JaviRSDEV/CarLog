package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.service.UserService;
import com.carlog.backend.service.WorkshopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/workshop")
@RequiredArgsConstructor
public class WorkshopController {

    private final WorkshopService workshopService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/details/{id}")
    public NewWorkshopDTO showById(@PathVariable Long id, Principal principal){
        return workshopService.getWorkshopById(id, principal.getName());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/{ID}/employees")
    public List<NewUserDTO> showEmployees(@PathVariable Long ID, Principal principal){
        return userService.getEmployeesByWorkshopId(ID, principal.getName());
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<NewWorkshopDTO> store(@Valid @RequestBody NewWorkshopDTO workshop, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.add(workshop, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PutMapping(value = "/details/{id}", consumes = { "multipart/form-data" })
    public NewWorkshopDTO update(
            @PathVariable Long id,
            @Valid @RequestPart("workshopData") NewWorkshopDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Principal principal) {

        return workshopService.edit(dto, id, file, principal.getName());
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/details/{id}")
    public NewWorkshopDTO destroy(@PathVariable Long id, Principal principal){
        return workshopService.delete(id, principal.getName());
    }
}