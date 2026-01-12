package com.carlog.backend.controller;

import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.service.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workshop")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "[*]")
public class WorkshopController {

    private final WorkshopService workshopService;

    @GetMapping
    public List<Workshop> index(){
        return workshopService.getAll();
    }

    @GetMapping("/{name}")
    public Workshop show(@PathVariable String name){
        return workshopService.getByWorkshopName(name);
    }

    @GetMapping("/{ID}/employees")
    public List<User> showEmployees(@PathVariable Long id){
        return workshopService.getEmployeesByWorkshopId(id);
    }

    @PostMapping
    public ResponseEntity<NewWorkshopDTO> store(@RequestBody NewWorkshopDTO workshop){
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.add(workshop));
    }

    @PutMapping("/{name}")
    public NewWorkshopDTO update(@RequestBody NewWorkshopDTO workshopData, @PathVariable String name){
        return workshopService.edit(workshopData, name);
    }

    @DeleteMapping("/{name}")
    public NewWorkshopDTO destroy(@PathVariable String name){
        return workshopService.delete(name);
    }
}
