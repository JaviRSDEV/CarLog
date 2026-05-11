package com.carlog.backend.repository;

import com.carlog.backend.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarBrandJpaRepository extends JpaRepository<CarBrand, Long> {

    Optional<CarBrand> findByName(String brandName);

    List<CarBrand> findAllByOrderByNameAsc();

    Optional<CarBrand> findByNameIgnoreCase(String name);
}