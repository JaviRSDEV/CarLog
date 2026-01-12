package com.carlog.backend.service;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.error.UserNotFoundException;
import com.carlog.backend.error.VehicleNotFoundException;
import com.carlog.backend.error.VehicleOcuppiedException;
import com.carlog.backend.error.WorkshopNotFoundException;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import com.carlog.backend.repository.WorkshopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<Vehicle> getAll(){
        var result = vehicleJpaRepository.findAll();
        if (result.isEmpty()) throw new VehicleNotFoundException();
        return result;
    }

    public List<Vehicle> getByWorkshop(Long workshopId){
        var result = vehicleJpaRepository.findByWorkshop_WorkshopId(workshopId);
        if(result.isEmpty()) throw new VehicleNotFoundException();
        return result;
    }

    public Vehicle getByPlate(String plate){
        return vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
    }

    public NewVehicleDTO add(NewVehicleDTO dto, String userDni){
        User connectedUser = userJpaRepository.findByDni(userDni).orElseThrow(() -> new UserNotFoundException(userDni));
        User owner;
        Workshop currentWorkshop;

        //Comprueba si es un mecanico el que esta registrando el vehiculo o si es un cliente
        boolean isWorker = connectedUser.getRole() == Role.MECHANIC || connectedUser.getRole() == Role.MANAGER;
        if(isWorker){
            if(dto.ownerId() != null && !dto.ownerId().isBlank()){
                owner = userJpaRepository.findByDni(dto.ownerId()).orElseThrow(() -> new UserNotFoundException(dto.ownerId()));
            }else{
                owner = connectedUser;
            }

            currentWorkshop = connectedUser.getWorkshop();
            if(currentWorkshop == null){
                throw new RuntimeException("Error: El mecanico o manager no dispone de taller asignado.");
            }
        }else{
            owner = connectedUser;
            currentWorkshop = null;
        }

        if(vehicleJpaRepository.findByPlate(dto.plate()).isPresent())
            throw new RuntimeException("Ya existe un vehiculo con la matricula " + dto.plate());

        var newVehicle = Vehicle.builder().plate(dto.plate()).brand(dto.brand()).model(dto.model()).kilometers(dto.kilometers()).engine(dto.engine()).horsePower(dto.horsePower()).torque(dto.torque()).tires(dto.tires()).image(dto.image()).lastMaintenance(dto.lastMaintenance()).owner(owner).workshop(currentWorkshop).build();
        return NewVehicleDTO.of(vehicleJpaRepository.save(newVehicle));
    }

    public NewVehicleDTO edit(NewVehicleDTO dto, String plate){
        return vehicleJpaRepository.findByPlate(plate).map(vehicle -> {
            if(!dto.plate().equals(vehicle.getPlate()) && vehicleJpaRepository.findByPlate(dto.plate()).isPresent()){
                throw new RuntimeException("La matrícula " + dto.plate() + " ya está en uso");
            }
            vehicle.setPlate(dto.plate());
            vehicle.setBrand(dto.brand());
            vehicle.setModel(dto.model());
            vehicle.setKilometers(dto.kilometers());
            vehicle.setEngine(dto.engine());
            vehicle.setHorsePower(dto.horsePower());
            vehicle.setTorque(dto.torque());
            vehicle.setTires(dto.tires());
            vehicle.setImage(dto.image());
            vehicle.setLastMaintenance(dto.lastMaintenance());
            if(dto.ownerId() != null){
                User u = userJpaRepository.findByDni(dto.ownerId()).orElseThrow(() -> new UserNotFoundException(dto.ownerId()));
                vehicle.setOwner(u);
            }

            return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
        }).orElseThrow(() -> new VehicleNotFoundException(plate));
    }

    //Metodo que evita que un taller pueda robarle el vehiculo a otro taller
    public NewVehicleDTO registerEntry(String plate, Long workshopId){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        Workshop newWorkshop = workshopJpaRepository.findById(workshopId).orElseThrow(() -> new WorkshopNotFoundException(workshopId));

        if(vehicle.getWorkshop() != null) {
            if (vehicle.getWorkshop().getWorkshopId() == workshopId)
                return NewVehicleDTO.of(vehicle);


            throw new VehicleOcuppiedException("El vehiculo esta registrado actualmente en el taller: " + vehicle.getWorkshop().getWorkshopName() +
                    " El cliente debe solicitar al taller que lo dé de baja primero");

        }
        vehicle.setWorkshop(newWorkshop);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO registerExit(String plate, Long workshopId){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));

        if(vehicle.getWorkshop() == null || vehicle.getWorkshop().getWorkshopId() != workshopId){
            throw new RuntimeException("No puedes dar salida a un coche que no esta registrado en el taller");
        }
        vehicle.setWorkshop(null);
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO changeOwner(String plate, String currentOwnerId, String newOwnerId){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        User newOwner = userJpaRepository.findByDni(newOwnerId).orElseThrow(() -> new UserNotFoundException(newOwnerId));
        if(vehicle.getOwner() != null && vehicle.getOwner().getDni().equals(currentOwnerId)){
            vehicle.setOwner(newOwner);
        }else{
            throw new RuntimeException("Error: El DNI proporcionado no corresponde al dueño actual del vehículo.");
        }
        return NewVehicleDTO.of(vehicleJpaRepository.save(vehicle));
    }

    public NewVehicleDTO delete(String plate){
        Vehicle vehicle = vehicleJpaRepository.findByPlate(plate).orElseThrow(() -> new VehicleNotFoundException(plate));
        vehicleJpaRepository.delete(vehicle);

        return NewVehicleDTO.of(vehicle);
    }
}
