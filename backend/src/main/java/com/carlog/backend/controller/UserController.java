package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.model.Role;
import com.carlog.backend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<NewUserDTO> show(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER', 'MECHANIC')")
    @GetMapping("/search/{name}")
    public List<NewUserDTO> searchByName(@PathVariable String name){
        return userService.getByName(name);
    }

    @PostMapping
    public ResponseEntity<NewUserDTO> store(@Valid @RequestBody NewUserDTO user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(user));
    }

    @PutMapping("/{DNI}")
    public NewUserDTO update(@Valid @RequestBody NewUserDTO userData, @PathVariable String DNI, @Parameter(hidden = true) Principal principal){
        return userService.edit(userData, DNI, principal.getName());
    }

    /*@DeleteMapping("/{DNI}")
    public NewUserDTO destroy(@PathVariable String DNI){
        return userService.delete(DNI);
    }*/

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{dni}/invite")
    public void invite(@PathVariable String dni, @RequestParam String managerDni, @RequestParam Role newRole, @Parameter(hidden = true) Principal principal){
        userService.inviteToWorkshop(principal.getName(), dni,  newRole);
    }

    @PatchMapping("/{dni}/accept")
    public NewUserDTO accept(@Parameter(hidden = true) Principal principal){
        return userService.acceptInvitation(principal.getName());
    }

    @PatchMapping("/{dni}/reject")
    public NewUserDTO reject(@PathVariable String dni, @Parameter(hidden = true) Principal principal){
        userService.rejectInvitation(principal.getName());
        return userService.getByDni(dni);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{dni}/fire")
    public void fireEmployee(@PathVariable String dni, @Parameter(hidden = true) Principal principal){
        userService.fireEmployee(principal.getName(), dni);
    }
}
