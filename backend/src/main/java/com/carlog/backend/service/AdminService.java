package com.carlog.backend.service;

import com.carlog.backend.dto.*;
import com.carlog.backend.model.CarVersion;
import com.carlog.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final WorkshopJpaRepository workshopJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;
    private final WorkOrderJpaRepository workOrderJpaRepository;
    private final CarBrandJpaRepository carBrandJpaRepository;
    private final CarModelJpaRepository carModelJpaRepository;
    private final CarVersionJpaRepository carVersionJpaRepository;

    public AdminStatsDTO getGlobalStats(){
        long workshops = workOrderJpaRepository.count();
        long users = userJpaRepository.count();
        long vehicles = vehicleJpaRepository.count();
        long orders = workOrderJpaRepository.count();
        long brands = carBrandJpaRepository.count();
        long models = carModelJpaRepository.count();
        long versions = carVersionJpaRepository.count();

        Double revenue = workOrderJpaRepository.sumTotalRevenue();

        return new AdminStatsDTO(workshops, users, vehicles, orders, revenue);
    }

    public Page<NewUserDTO> getAllUsers(Pageable pageable){
        return userJpaRepository.findAll(pageable)
                .map(NewUserDTO::of);
    }

    public Page<NewWorkshopDTO> getAllWorkshops(Pageable pageable){
        return workshopJpaRepository.findAll(pageable)
                .map(NewWorkshopDTO::of);
    }

    public Page<NewVehicleDTO> getAllVehicles(Pageable pageable){
        return vehicleJpaRepository.findAll(pageable)
                .map(NewVehicleDTO::of);
    }

    public Page<NewWorkOrderResponseDTO> getAllWorkOrders(Pageable pageable){
        return workOrderJpaRepository.findAll(pageable)
                .map(NewWorkOrderResponseDTO::of);
    }

    public Page<CarBrandDTO> getAllCarBrands(Pageable pageable){
        return carBrandJpaRepository.findAll(pageable).map(CarBrandDTO::of);
    }

    public Page<CarModelDTO> getAllCarModels(Pageable pageable){
        return carModelJpaRepository.findAll(pageable).map(CarModelDTO:: of);
    }

    public Page<CarVersionDTO> getAllCarVersions(Pageable pageable){
        return carVersionJpaRepository.findAll(pageable).map(CarVersionDTO::of);
    }
}
