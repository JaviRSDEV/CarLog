package com.carlog.backend.repository;

import com.carlog.backend.model.CarVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarVersionJpaRepository extends JpaRepository<CarVersion, Long> {

    List<CarVersion> findByCarModelId(Long modelId);

    boolean existsByCarModel_IdAndVersionNameAndEngineCode(Long modelId, String versionName, String engineCode);
}
