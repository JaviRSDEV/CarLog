package com.carlog.backend.repository;

import com.carlog.backend.model.User;
import com.carlog.backend.model.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByPlate(String plate);

    @Transactional
    void deleteByPlate(String plate);

    Page<Vehicle> findByWorkshop_WorkshopId(Long workshopId, Pageable pageable);
    Page<Vehicle> findByOwner_Dni(String dni, Pageable pageable);

    @Query("SELECT DISTINCT v.owner FROM Vehicle v WHERE v.workshop.workshopId = :workshopId")
    List<User> findClientsByWorkshopId(@Param("workshopId") Long workshopId);

    @Query("SELECT v FROM Vehicle v WHERE v.owner.dni = :ownerDni " +
            "AND (LOWER(v.plate) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.model) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Vehicle> searchByOwnerAndText(@Param("ownerDni") String ownerDni, @Param("text") String text, Pageable pageable);

    @Query("SELECT v FROM Vehicle v WHERE v.workshop.workshopId = :workshopId " +
            "AND (LOWER(v.plate) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(v.model) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Vehicle> searchByWorkshopAndText(@Param("workshopId") Long workshopId, @Param("text") String text, Pageable pageable);

    @Query("SELECT DISTINCT w.vehicle FROM WorkOrder w WHERE w.mechanic.dni = :mechanicDni")
    Page<Vehicle> findDistinctVehiclesByMechanicDni(@Param("mechanicDni") String mechanicDni, Pageable pageable);

    @Query("SELECT DISTINCT w.vehicle FROM WorkOrder w WHERE w.mechanic.dni = :mechanicDni " +
            "AND w.status != 'COMPLETED' " +
            "AND (LOWER(w.vehicle.plate) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(w.vehicle.brand) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(w.vehicle.model) LIKE LOWER(CONCAT('%', :text, '%')))")
    Page<Vehicle> searchDistinctVehiclesByMechanicDniAndText(@Param("mechanicDni") String mechanicDni, @Param("text") String text, Pageable pageable);
}