package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.service.UserService;
import com.carlog.backend.service.WorkshopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @GetMapping("/details/{id}")
    public NewWorkshopDTO showById(@PathVariable Long id){
        return workshopService.getWorkshopById(id);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/{ID}/employees")
    public List<NewUserDTO> showEmployees(@PathVariable Long ID){
        return userService.getEmployeesByWorkshopId(ID);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<NewWorkshopDTO> store(@RequestBody NewWorkshopDTO workshop, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.add(workshop, principal.getName()));
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PutMapping(value = "/details/{id}", consumes = { "multipart/form-data" })
    public NewWorkshopDTO update(
            @PathVariable Long id,
            @RequestPart("workshopData") String workshopDataJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Principal principal) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        NewWorkshopDTO dto = objectMapper.readValue(workshopDataJson, NewWorkshopDTO.class);

        return workshopService.edit(dto, id, file, principal.getName());
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/details/{id}")
    public NewWorkshopDTO destroy(@PathVariable Long id, Principal principal){
        return workshopService.delete(id, principal.getName());
    }
}