package com.carlog.backend.controller;

import com.carlog.backend.dto.AlertDTO;
import com.carlog.backend.service.AlertService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<AlertDTO>> getAllMyAlerts(@Parameter(hidden = true)Principal principal){
        List<AlertDTO> alerts = alertService.getAlertsByUser(principal.getName());
        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public ResponseEntity<AlertDTO> storeAlert(@Valid @RequestBody AlertDTO alertDTO, @Parameter(hidden = true) Principal principal){
        AlertDTO createdAlert = alertService.createAlert(alertDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertDTO> updatedAlert(@PathVariable Long id, @Valid @RequestBody AlertDTO alertDTO, @Parameter(hidden = true) Principal principal){
        return ResponseEntity.ok(alertService.updatedAlert(id, alertDTO, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id, @Parameter(hidden = true) Principal principal){
        alertService.deleteAlert(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
