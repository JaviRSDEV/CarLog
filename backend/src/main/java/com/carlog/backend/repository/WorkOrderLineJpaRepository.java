package com.carlog.backend.repository;

import com.carlog.backend.model.WorkOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkOrderLineJpaRepository extends JpaRepository<WorkOrderLine, Long> {
    Optional<WorkOrderLine> findByWorkOrder_Id(String workOrder_id);
}
