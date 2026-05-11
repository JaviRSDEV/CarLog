package com.carlog.backend.service;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class CarCatalogService {
    private final CarBrandJpaRepository brandJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final RestClient restClient = RestClient.create();

    public void syncCatalog() {
        if (brandJpaRepository.count() > 500) {
            log.info("El catálogo ya contiene datos, saltando sincronización inicial.");
            return;
        }

        log.info("Iniciando carga del catálogo de vehículos desde NHTSA...");

        try {
            String makesUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/car?format=json";
            NhtsaMakeResponse makesResponse = restClient.get().uri(makesUrl).retrieve().body(NhtsaMakeResponse.class);

            if (makesResponse == null || makesResponse.Results == null) return;

            log.info("Procesando {} marcas...", makesResponse.Results.size());

            for (NhtsaMakeResult makeResult : makesResponse.Results) {
                try {
                    processBrandSync(makeResult.MakeName.trim());
                } catch (Exception e) {
                    log.error("Error omitiendo marca {}: {}", makeResult.MakeName, e.getMessage());
                }
            }

            log.info("¡Sincronización masiva completada!");

        } catch (Exception e) {
            log.error("Error de conexión con la API: {}", e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processBrandSync(String brandName) {
        CarBrand brand = brandJpaRepository.findByName(brandName)
                .orElseGet(() -> brandJpaRepository.save(CarBrand.builder().name(brandName).build()));

        String modelsUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/" + brandName + "?format=json";
        NhtsaModelResponse modelsResponse = restClient.get().uri(modelsUrl).retrieve().body(NhtsaModelResponse.class);

        if (modelsResponse != null && modelsResponse.Results != null) {
            List<CarModel> newModels = modelsResponse.Results.stream()
                    .map(res -> res.Model_Name.trim())
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

    private record NhtsaMakeResponse(List<NhtsaMakeResult> Results) {}
    private record NhtsaMakeResult(String MakeName) {}
    private record NhtsaModelResponse(List<NhtsaModelResult> Results) {}
    private record NhtsaModelResult(String Model_Name) {}
}