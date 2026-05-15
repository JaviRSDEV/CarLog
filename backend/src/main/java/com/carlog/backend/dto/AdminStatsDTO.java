package com.carlog.backend.dto;

public record AdminStatsDTO(
        long totalWorkshops,
        long totalUsers,
        long totalVehicles,
        long totalWorkOrders,
        double totalRevenue
) {}
