package com.carlog.backend.repository;

import com.carlog.backend.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelJpaRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByBrand_NameIgnoreCase(String brandName);
}
