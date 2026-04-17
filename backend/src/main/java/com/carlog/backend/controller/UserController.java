package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<NewUserDTO> index() {
        return userService.getAll();
    }

    @GetMapping("/{DNI}")
    public NewUserDTO show(@PathVariable String DNI){
        return userService.getByDni(DNI);
    }

    @GetMapping("/search/{name}")
    public List<NewUserDTO> searchByName(@PathVariable String name){
        return userService.getByName(name);
    }

    @PostMapping
    public ResponseEntity<NewUserDTO> store(@RequestBody NewUserDTO user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(user));
    }

    @PutMapping("/{DNI}")
    public NewUserDTO update(@RequestBody NewUserDTO userData, @PathVariable String DNI){
        return userService.edit(userData, DNI);
    }

    @DeleteMapping("/{DNI}")
    public NewUserDTO destroy(@PathVariable String DNI){
        return userService.delete(DNI);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{dni}/invite")
    public void invite(@PathVariable String dni, @RequestParam String managerDni, @RequestParam Role newRole){
        userService.inviteToWorkshop(managerDni, dni,  newRole);
    }

    @PatchMapping("/{dni}/accept")
    public NewUserDTO accept(@PathVariable String dni){
        return userService.acceptInvitation(dni);
    }

    @PatchMapping("/{dni}/reject")
    public NewUserDTO reject(@PathVariable String dni){
        userService.rejectInvitation(dni);
        return userService.getByDni(dni);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'CO_MANAGER')")
    @PatchMapping("/{dni}/fire")
    public void fireEmployee(@PathVariable String dni){
        userService.fireEmployee(dni);
    }
}
