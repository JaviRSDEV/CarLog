package com.carlog.backend.repository;

import com.carlog.backend.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandJpaRepository extends JpaRepository<CarBrand, Long> {
}
