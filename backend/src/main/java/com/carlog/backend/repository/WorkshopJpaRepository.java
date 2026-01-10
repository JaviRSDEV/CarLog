package com.carlog.backend.repository;

import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkshopJpaRepository extends JpaRepository<Workshop, Long> {
    Optional<Workshop> findById(String dni);
    Optional<Workshop> findByWorkshopName(String workshopName);

    @Transactional
    void deleteById(String id);
    Optional<User> findFirstByWorkshopName(String workshopName);
}
