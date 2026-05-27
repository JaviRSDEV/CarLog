package com.carlog.backend.service;

import com.carlog.backend.dto.AlertDTO;
import com.carlog.backend.error.AlertNotFoundException;
import com.carlog.backend.error.UnauthorizedActionException;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.VehicleNotFoundException;
import com.carlog.backend.model.Alert;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import com.carlog.backend.repository.AlertJpaRepository;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

    private final AlertJpaRepository alertJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;

    public List<AlertDTO> getAlertsByUser(String email) {
        return alertJpaRepository.findByUserEmail(email).stream()
                .map(AlertDTO::of)
                .toList();
    }

    @Transactional
    public AlertDTO createAlert(AlertDTO dto, String email) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Vehicle vehicle = vehicleJpaRepository.findByPlate(dto.vehiclePlate())
                .orElseThrow(() -> new VehicleNotFoundException("Matrícula inexistente: " + dto.vehiclePlate()));

        Alert alert = Alert.builder()
                .title(dto.title())
                .description(dto.description())
                .dueDate(dto.dueDate())
                .user(user)
                .vehicle(vehicle)
                .notifiedOneWeek(false)
                .notifiedToday(false)
                .build();

        Alert savedAlert = alertJpaRepository.save(alert);
        log.info("Nueva alerta automatizada registrada para el coche {} por el usuario {}", dto.vehiclePlate(), email);

        return AlertDTO.of(savedAlert);
    }

    @Transactional
    public AlertDTO updatedAlert(Long id, AlertDTO dto, String email){
        Alert alert = alertJpaRepository.findById(id)
                .orElseThrow(() -> new AlertNotFoundException("Alerta no encontrada con ID: " + id));

        if(!alert.getUser().getEmail().equals(email)){
            throw new UnauthorizedActionException("No tienes permisos para modificar esta alerta");
        }

        alert.setTitle(dto.title());
        alert.setDescription(dto.description());
        alert.setDueDate(dto.dueDate());

        alert.setNotifiedOneWeek(false);
        alert.setNotifiedToday(false);

        return AlertDTO.of(alertJpaRepository.save(alert));
    }

    @Transactional
    public void deleteAlert(Long id, String email){
        Alert alert = alertJpaRepository.findById(id)
                .orElseThrow(() -> new AlertNotFoundException("Alerta no encontrada con ID: " + id));

        if(!alert.getUser().getEmail().equals(email)){
            throw new UnauthorizedActionException("No tienes permisos para eliminar esta alerta");
        }

        alertJpaRepository.delete(alert);
    }
}
