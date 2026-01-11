package com.carlog.backend.repository;

import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {
    //Busqueda por matrícula
    Optional<Vehicle> findByPlate(String plate);
    //Eliminar buscando la matrícula
    @Transactional
    void deleteByPlate(String plate);
    //Busqueda por marca
    List<Vehicle> findByBrandIgnoreCase(String brand);
    //Busqueda por modelo
    List<Vehicle> findByModelIgnoreCase(String model);
    //Busqueda por motor
    List<Vehicle> findByEngineIgnoreCase(String engine);
    //Lista de vehiculos que actualmente estan dentro del taller
    List<Vehicle> findByWorkshop_WorkshopId(Long workshopId);

    //Listado de clientes del taller, sacados a partir del campo de dueños de los vehiculos
    @Query("SELECT DISTINCT v.owner FROM Vehicle v WHERE v.workshop.id = :workshopId")
    List<User> findClientsByWorkshopId(@Param("workshopId") Long workshopId);
}
