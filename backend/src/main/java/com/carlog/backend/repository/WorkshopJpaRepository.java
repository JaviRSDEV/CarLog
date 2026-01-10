package com.carlog.backend.repository;

import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkshopJpaRepository extends JpaRepository<Workshop, Long> {
    Optional<Workshop> findByWorkshopName(String workshopName);

    List<User> findUserByWorkshopId(@Param("id") Long workShopId);

    void deleteByWorkshopName(String workshopName);
}
