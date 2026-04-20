package com.carlog.backend.repository;

import com.carlog.backend.dto.NewVehicleDTO;
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

    List<Vehicle> findByOwner_Dni(String dni);

    //Listado de clientes del taller, sacados a partir del campo de dueños de los vehiculos
    @Query("SELECT DISTINCT v.owner FROM Vehicle v WHERE v.workshop.workshopId = :workshopId")
    List<User> findClientsByWorkshopId(@Param("workshopId") Long workshopId);

    // Búsqueda para el cliente (Mis coches)
    @Query("SELECT v FROM Vehicle v WHERE v.owner.dni = :ownerDni " +
            "AND (LOWER(v.plate) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.model) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<Vehicle> searchByOwnerAndText(@Param("ownerDni") String ownerDni, @Param("text") String text);

    // Búsqueda para la Flota del taller
    @Query("SELECT v FROM Vehicle v WHERE v.workshop.workshopId = :workshopId " +
            "AND (LOWER(v.plate) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.model) LIKE LOWER(CONCAT('%', :text, '%')))")
    List<Vehicle> searchByWorkshopAndText(@Param("workshopId") Long workshopId, @Param("text") String text);
}
