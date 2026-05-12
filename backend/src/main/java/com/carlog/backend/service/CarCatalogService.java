package com.carlog.backend.service;

import com.carlog.backend.dto.NhtsaMakeResponse;
import com.carlog.backend.dto.NhtsaMakeResult;
import com.carlog.backend.model.CarBrand;
import com.carlog.backend.model.CarModel;
import com.carlog.backend.model.CarVersion;
import com.carlog.backend.repository.CarBrandJpaRepository;
import com.carlog.backend.repository.CarModelJpaRepository;
import com.carlog.backend.repository.CarVersionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    private final BrandSyncService brandSyncService;

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

            if (makesResponse == null || makesResponse.Results() == null) {
                log.warn("La API de NHTSA no devolvió resultados.");
                return;
            }

            log.info("Procesando {} marcas encontradas...", makesResponse.Results().size());

            for (NhtsaMakeResult makeResult : makesResponse.Results()) {
                try {
                    brandSyncService.processBrandSync(makeResult.MakeName().trim());
                } catch (Exception e) {
                    log.error("Error omitiendo marca {}: {}", makeResult.MakeName(), e.getMessage());
                }
            }

            log.info("¡Sincronización manual completada!");

        } catch (Exception e) {
            log.error("Error de conexión con la API externa: {}", e.getMessage());
        }
    }
}