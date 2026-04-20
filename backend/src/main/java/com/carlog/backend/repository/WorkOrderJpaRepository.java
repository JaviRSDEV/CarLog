package com.carlog.backend.repository;

import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrder, Long> {
    Page<WorkOrder> findByVehicle_Plate(String plate, Pageable pageable);
    List<WorkOrder> findByMechanic_Dni(String dni);

    Optional<WorkOrder> findByStatus(WorkOrderStatus status);
    List<WorkOrder> findByWorkshop_workshopId(Long workshopId);
}
