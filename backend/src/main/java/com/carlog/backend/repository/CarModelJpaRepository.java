package com.carlog.backend.repository;

import com.carlog.backend.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelJpaRepository extends JpaRepository<CarModel, Long> {

    List<CarModel> findByBrand_NameIgnoreCaseOrderByNameAsc(String brandName);

    boolean existsByNameAndBrand_Id(String modelName, Long brandId);
}