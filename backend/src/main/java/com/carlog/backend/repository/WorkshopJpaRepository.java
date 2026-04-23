package com.carlog.backend.repository;

import com.carlog.backend.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkshopJpaRepository extends JpaRepository<Workshop, Long> {
    Optional<Workshop> findByWorkshopName(String workshopName);

    void deleteByWorkshopName(String workshopName);
}
