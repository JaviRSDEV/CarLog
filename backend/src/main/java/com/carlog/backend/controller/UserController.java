package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.model.User;
import com.carlog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> index() {
        return userService.getAll();
    }

    @GetMapping("/{DNI}")
    public User show(@PathVariable String DNI){
        return userService.getByDni(DNI);
    }

    @GetMapping("/search/{name}")
    public List<User> searchByName(@PathVariable String name){
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
}
