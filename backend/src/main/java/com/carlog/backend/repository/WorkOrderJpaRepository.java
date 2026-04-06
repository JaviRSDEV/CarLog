package com.carlog.backend.repository;

import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByVehicle_Plate(String plate);
    List<WorkOrder> findByMechanic_Dni(String dni);

    Optional<WorkOrder> findByStatus(WorkOrderStatus status);
    Optional<WorkOrder> findByWorkshop_workshopId(Long workshopId);
}
