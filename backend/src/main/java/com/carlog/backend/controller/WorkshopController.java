package com.carlog.backend.controller;

import com.carlog.backend.dto.NewUserDTO;
import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.service.UserService;
import com.carlog.backend.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // 🔥 Asegúrate de tener este import
import java.util.List;

@RestController
@RequestMapping("/api/workshop")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final UserService userService;

    @GetMapping
    public List<NewWorkshopDTO> index(){
        return workshopService.getAll();
    }

    @GetMapping("/{name}")
    public NewWorkshopDTO show(@PathVariable String name){
        return workshopService.getByWorkshopName(name);
    }

    @GetMapping("/details/{id}")
    public NewWorkshopDTO showById(@PathVariable Long id){
        return workshopService.getWorkshopById(id);
    }

    @GetMapping("/{ID}/employees")
    public List<NewUserDTO> showEmployees(@PathVariable Long ID){
        return userService.getEmployeesByWorkshopId(ID);
    }

    @PostMapping
    public ResponseEntity<NewWorkshopDTO> store(@RequestBody NewWorkshopDTO workshop, Principal principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.add(workshop, principal.getName()));
    }

    @PutMapping("/{name}")
    public NewWorkshopDTO update(@RequestBody NewWorkshopDTO workshopData, @PathVariable String name, Principal principal){
        return workshopService.edit(workshopData, name, principal.getName());
    }

    @DeleteMapping("/{name}")
    public NewWorkshopDTO destroy(@PathVariable String name, Principal principal){
        return workshopService.delete(name, principal.getName());
    }
}