package com.carlog.backend.repository;

import com.carlog.backend.model.Vehicle;
import com.carlog.backend.model.WorkOrder;
import com.carlog.backend.model.WorkOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkOrderJpaRepository extends JpaRepository<WorkOrder, Long> {
    Page<WorkOrder> findByVehicle_Plate(String plate, Pageable pageable);
    List<WorkOrder> findByMechanic_Dni(String dni);

    Optional<WorkOrder> findByStatus(WorkOrderStatus status);
    List<WorkOrder> findByWorkshop_workshopId(Long workshopId);

    List<WorkOrder> findAllByVehicle(Vehicle vehicle);

    @Query("SELECT COALESCE(SUM(w.totalAmount), 0) FROM WorkOrder w WHERE w.paymentStatus = 'PAID'")
    Double sumTotalRevenue();
}