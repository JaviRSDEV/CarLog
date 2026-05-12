package com.carlog.backend.service;

import com.carlog.backend.dto.NhtsaModelResponse;
import com.carlog.backend.model.CarBrand;
import com.carlog.backend.model.CarModel;
import com.carlog.backend.repository.CarBrandJpaRepository;
import com.carlog.backend.repository.CarModelJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandSyncService {

    private final CarBrandJpaRepository carBrandJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final RestClient restClient;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processBrandSync(String brandName) {
        CarBrand brand = carBrandJpaRepository.findByName(brandName)
                .orElseGet(() -> carBrandJpaRepository.save(CarBrand.builder().name(brandName).build()));

        String modelsUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/" + brandName + "?format=json";

        NhtsaModelResponse modelsResponse = restClient.get()
                .uri(modelsUrl)
                .retrieve()
                .body(NhtsaModelResponse.class);

        if (modelsResponse != null && modelsResponse.Results() != null) {
            List<CarModel> newModels = modelsResponse.Results().stream()
                    .map(res -> res.Model_Name().trim())
                    .distinct()
                    .filter(modelName -> !carModelJpaRepository.existsByNameAndBrand_Id(modelName, brand.getId()))
                    .map(modelName -> CarModel.builder().name(modelName).brand(brand).build())
                    .toList();

            if (!newModels.isEmpty()) {
                carModelJpaRepository.saveAll(newModels);
                log.info("Sincronizados {} nuevos modelos para {}", newModels.size(), brandName);
            }
        }
    }
}