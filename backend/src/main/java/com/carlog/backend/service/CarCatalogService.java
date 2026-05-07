package com.carlog.backend.service;

import com.carlog.backend.model.CarBrand;
import com.carlog.backend.model.CarModel;
import com.carlog.backend.repository.CarBrandJpaRepository;
import com.carlog.backend.repository.CarModelJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarCatalogService {
    private final CarBrandJpaRepository brandJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final RestClient restClient = RestClient.create();

    @Transactional
    public void syncCatalog() {
        if (brandJpaRepository.count() > 0) return;

        log.info("Iniciando carga del catálogo de vehículos");

        try {
            String makesUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/car?format=json";
            NhtsaMakeResponse makesResponse = restClient.get().uri(makesUrl).retrieve().body(NhtsaMakeResponse.class);

            if (makesResponse == null || makesResponse.Results == null) {
                log.error("No se pudieron obtener las marcas de la API.");
                return;
            }

            log.info("¡Se han encontrado {} marcas! Preparando descarga de modelos (esto puede tardar unos minutos)...",
                    makesResponse.Results.size());

            for (NhtsaMakeResult makeResult : makesResponse.Results) {
                String brandName = makeResult.MakeName.trim();

                try {
                    CarBrand brand = brandJpaRepository.save(CarBrand.builder().name(brandName).build());

                    String modelsUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/" + brandName + "?format=json";
                    NhtsaModelResponse modelsResponse = restClient.get().uri(modelsUrl).retrieve().body(NhtsaModelResponse.class);

                    if (modelsResponse != null && modelsResponse.Results != null && !modelsResponse.Results.isEmpty()) {
                        List<CarModel> models = modelsResponse.Results.stream()
                                .map(res -> CarModel.builder().name(res.Model_Name.trim()).brand(brand).build())
                                .distinct()
                                .toList();

                        carModelJpaRepository.saveAll(models);
                        log.info("Sincronizada marca: {} con {} modelos", brandName, models.size());
                    }
                } catch (Exception e) {
                    log.error("Error cargando marca {}: {}", brandName, e.getMessage());
                }
            }

            log.info("¡Sincronización masiva de la base de datos completada con éxito!");

        } catch (Exception e) {
            log.error("Error catastrófico conectando con la API de NHTSA: {}", e.getMessage());
        }
    }

    private record NhtsaMakeResponse(List<NhtsaMakeResult> Results) {}
    private record NhtsaMakeResult(String MakeName) {}

    private record NhtsaModelResponse(List<NhtsaModelResult> Results) {}
    private record NhtsaModelResult(String Model_Name) {}
}
