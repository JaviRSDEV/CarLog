package com.carlog.backend.repository;

import com.carlog.backend.model.WorkOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByVehicle_Plate(String plate);
    List<WorkOrder> findByMechanic_Dni(String dni);
    @Transactional
    void deleteById(String id);
    Optional<WorkOrder> findByStatus(String status);
    Optional<WorkOrder> findByWorkshop_workshopId(String workshopId);
}
