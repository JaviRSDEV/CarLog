package com.carlog.backend.service;

import com.carlog.backend.dto.NewVehicleDTO;
import com.carlog.backend.dto.NewWorkshopDTO;
import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import com.carlog.backend.model.Workshop;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.repository.VehicleJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private VehicleJpaRepository vehicleJpaRepository;
    @InjectMocks
    private VehicleService vehicleService;
    @Captor
    private ArgumentCaptor<Vehicle> vehicleCaptor;
    @Test
    void addVehicleWhenUserIsClient(){

        String userDni = "12345678A";
        NewVehicleDTO inputVehicle = new NewVehicleDTO("9999-ZZZ", "Toyota", "Corolla", (long) 100.0,
                                                "Hybrid", 120, 150, "Michelin",
                                                 null, null, null, null);
        User fakeClient = User.builder().dni(userDni).role(Role.CLIENT).build();

        Vehicle savedVehicle = Vehicle.builder().plate("9999-ZZZ").brand("Toyota").owner(fakeClient).build();

        when(userJpaRepository.findByDni(userDni)).thenReturn(Optional.of(fakeClient));

        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn(savedVehicle);

        NewVehicleDTO result = vehicleService.add(inputVehicle, userDni);

        assertNotNull(result);
        assertEquals("9999-ZZZ", result.plate());

        verify(userJpaRepository).findByDni(userDni);
        verify(vehicleJpaRepository).save(any(Vehicle.class));

    }

    @Test
    void addVehicleWhenUserIsMechanic(){
        String userDni = "12345678A";
        Workshop fakeWorkshop = Workshop.builder().workshopId(1).build();
        NewVehicleDTO inputVehicle = new NewVehicleDTO("9999-ZZZ", "Toyota", "Corolla", (long) 100.0,
                "Hybrid", 120, 150, "Michelin",
                null, null, null, null);

        User fakeMechanic = User.builder().dni(userDni).role(Role.MECHANIC).workshop(fakeWorkshop).build();
        Vehicle savedVehicle = Vehicle.builder().plate("9999-ZZZ").brand("Toyota").workshop(fakeWorkshop).build();

        when(userJpaRepository.findByDni(userDni)).thenReturn(Optional.of(fakeMechanic));
        when(vehicleJpaRepository.save(any(Vehicle.class))).thenReturn( new Vehicle());

        vehicleService.add(inputVehicle, userDni);

        verify(vehicleJpaRepository).save(vehicleCaptor.capture());

        Vehicle vehicleStored = vehicleCaptor.getValue();

        assertNotNull(vehicleStored.getWorkshop(), "El coche debería tener un taller asignado");
        assertEquals("9999-ZZZ", vehicleStored.getPlate(), "Las matrículas deberían de coincidir");

        assertEquals(vehicleStored.getWorkshop().getWorkshopId(), fakeMechanic.getWorkshop().getWorkshopId(), "El ID de taller del mecanico y del coche deben de coincidir");
        assertEquals(savedVehicle.getWorkshop().getWorkshopId(), vehicleStored.getWorkshop().getWorkshopId(), "EL ID de taller de ambos coches deben de coincidir");
    }

    @Test
    void addVehicleWhenUserDoestNotExist(){
        String fakeDni = "000000X";
        NewVehicleDTO savedVehicle = new NewVehicleDTO("9999-ZZZ", "Fantasma", "CAR", (long) 100.0,
                                          "GAS", 120, 150,
                                            null, null, null, null, null);

        when(userJpaRepository.findByDni(fakeDni)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->{
            vehicleService.add(savedVehicle, fakeDni);
        });

        verify(vehicleJpaRepository, never()).save(any());
    }
}
