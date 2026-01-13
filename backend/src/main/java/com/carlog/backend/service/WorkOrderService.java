package com.carlog.backend.service;

import com.carlog.backend.dto.NewWorkOrderDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.VehicleNotFoundException;
import com.carlog.backend.error.WorkOrderNotFoundException;
import com.carlog.backend.model.*;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;

    public List<WorkOrder> getAll(){
        var result = workOrderJpaRepository.findAll();
        if(result.isEmpty()) throw new WorkOrderNotFoundException();
        return result;
    }

    public WorkOrder getByEmployee(String dni){
        return workOrderJpaRepository.findByMechanic_Dni(dni).orElseThrow(() -> new WorkOrderNotFoundException());
    }

    public WorkOrder getByVehicle(String plate){
        return workOrderJpaRepository.findByVehicle_Plate(plate).orElseThrow(() -> new WorkOrderNotFoundException());
    }

    public NewWorkOrderDTO add(NewWorkOrderDTO dto, String userDni, String vehiclePlate){
        User connectedUser = userJpaRepository.findByDni(userDni).orElseThrow(() -> new UserNotFoundException(userDni));
        Vehicle referedVehicle = vehicleJpaRepository.findByPlate(vehiclePlate).orElseThrow(() -> new VehicleNotFoundException(vehiclePlate));

        boolean isWorker = connectedUser.getRole() == Role.MECHANIC || connectedUser.getRole() == Role.MANAGER || connectedUser.getRole() == Role.CO_MANAGER || connectedUser.getRole() == Role.DIY;
        if(isWorker){
            if(connectedUser.getWorkshop() == null && connectedUser.getRole() != Role.DIY){
                throw new RuntimeException("Error: El mecanico o manager no dispone de un taller asignado");
            }

            var newWorkOrder = WorkOrder.builder().description(dto.description()).mechanicNotes(null).status(WorkOrderStatus.PENDING).vehicle(referedVehicle).mechanic(connectedUser);
        }
    }


}
