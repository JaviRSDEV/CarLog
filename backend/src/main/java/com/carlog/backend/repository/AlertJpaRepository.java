package com.carlog.backend.repository;

import com.carlog.backend.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlertJpaRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserEmail(String email);
    List<Alert> findByDueDateAndNotifiedOneWeekFalse(LocalDate date);
    List<Alert> findByDueDateAndNotifiedTodayFalse(LocalDate date);
}
