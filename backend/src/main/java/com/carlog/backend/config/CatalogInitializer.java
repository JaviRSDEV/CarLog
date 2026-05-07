package com.carlog.backend.config;

import com.carlog.backend.service.CarCatalogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CatalogInitializer implements CommandLineRunner {

    private final CarCatalogService carCatalogService;

    public CatalogInitializer(CarCatalogService carCatalogService) {
        this.carCatalogService = carCatalogService;
    }

    @Override
    public void run(String... args) {
        carCatalogService.syncCatalog();
    }
}
