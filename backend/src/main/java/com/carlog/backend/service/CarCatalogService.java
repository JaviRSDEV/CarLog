package com.carlog.backend.service;

import com.carlog.backend.model.CarBrand;
import com.carlog.backend.model.CarModel;
import com.carlog.backend.model.CarVersion;
import com.carlog.backend.repository.CarBrandJpaRepository;
import com.carlog.backend.repository.CarModelJpaRepository;
import com.carlog.backend.repository.CarVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarCatalogService {

    private final CarBrandJpaRepository brandJpaRepository;
    private final CarModelJpaRepository modelJpaRepository;
    private final CarVersionJpaRepository versionJpaRepository;
    private final RestClient restClient = RestClient.create();

    @Transactional(readOnly = true)
    public List<CarBrand> getAllBrands() {
        return brandJpaRepository.findAllByOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public List<CarModel> getModelsByBrand(Long brandId) {
        return modelJpaRepository.findByBrandIdOrderByNameAsc(brandId);
    }

    @Transactional(readOnly = true)
    public List<CarVersion> getVersionsByModel(Long modelId) {
        return versionJpaRepository.findByCarModelIdOrderByVersionNameAsc(modelId);
    }

    @Transactional(readOnly = true)
    public CarVersion getVersionById(Long versionId) {
        return versionJpaRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada"));
    }

    public void syncCatalog() {
        log.info("Iniciando sincronización manual del catálogo desde NHTSA...");

        try {
            String makesUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/car?format=json";
            NhtsaMakeResponse makesResponse = restClient.get()
                    .uri(makesUrl)
                    .retrieve()
                    .body(NhtsaMakeResponse.class);

            if (makesResponse == null || makesResponse.Results == null) {
                log.warn("La API de NHTSA no devolvió resultados.");
                return;
            }

            log.info("Procesando {} marcas encontradas...", makesResponse.Results.size());

            for (NhtsaMakeResult makeResult : makesResponse.Results) {
                try {
                    processBrandSync(makeResult.MakeName.trim());
                } catch (Exception e) {
                    log.error("Error omitiendo marca {}: {}", makeResult.MakeName, e.getMessage());
                }
            }

            log.info("¡Sincronización manual completada!");

        } catch (Exception e) {
            log.error("Error de conexión con la API externa: {}", e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processBrandSync(String brandName) {
        // Buscamos o creamos la marca
        CarBrand brand = brandJpaRepository.findByName(brandName)
                .orElseGet(() -> brandJpaRepository.save(CarBrand.builder().name(brandName).build()));

        String modelsUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/" + brandName + "?format=json";
        NhtsaModelResponse modelsResponse = restClient.get()
                .uri(modelsUrl)
                .retrieve()
                .body(NhtsaModelResponse.class);

        if (modelsResponse != null && modelsResponse.Results != null) {
            List<CarModel> newModels = modelsResponse.Results.stream()
                    .map(res -> res.Model_Name.trim())
                    .distinct()
                    .filter(modelName -> !modelJpaRepository.existsByNameAndBrand_Id(modelName, brand.getId()))
                    .map(modelName -> CarModel.builder().name(modelName).brand(brand).build())
                    .toList();

            if (!newModels.isEmpty()) {
                modelJpaRepository.saveAll(newModels);
                log.info("Sincronizados {} nuevos modelos para {}", newModels.size(), brandName);
            }
        }
    }

    private record NhtsaMakeResponse(List<NhtsaMakeResult> Results) {}
    private record NhtsaMakeResult(String MakeName) {}
    private record NhtsaModelResponse(List<NhtsaModelResult> Results) {}
    private record NhtsaModelResult(String Model_Name) {}
}