package com.carlog.backend.repository;

import com.carlog.backend.model.WorkOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    Optional<WorkOrder> findByVehicle_Plate(String plate);
    Optional<WorkOrder> findByMechanic_Dni(String dni);
    @Transactional
    void deleteById(String id);
    Optional<WorkOrder> findByStatus(String status);
    Optional<WorkOrder> findByWorkshop_workshopId(String workshopId);
}
