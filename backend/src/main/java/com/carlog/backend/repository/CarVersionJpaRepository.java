package com.carlog.backend.repository;

import com.carlog.backend.model.CarVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarVersionJpaRepository extends JpaRepository<CarVersion, Long> {

    List<CarVersion> findByCarModelIdOrderByVersionNameAsc(Long modelId);

    boolean existsByCarModel_IdAndVersionNameAndEngineCode(Long modelId, String versionName, String engineCode);

    Optional<CarVersion> findByVersionNameIgnoreCaseAndCarModelId(String versionName, Long modelId);
}