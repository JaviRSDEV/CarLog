package com.carlog.backend.dto;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.model.Workshop;

public record NewUserDTO(String dni,
                         String name,
                         String email,
                         String phone,
                         Role role,
                         Boolean mustChangePassword,
                         Long workShopId,
                         Long pendingWorkshop,
                         String pendingWorkshopName) {

    public static NewUserDTO of(User u){
        return new NewUserDTO(
                u.getDni(),
                u.getName(),
                u.getEmail(),
                u.getPhone(),
                u.getRole(),
                u.isMustChangePsswd(),
                u.getWorkshop() != null ? u.getWorkshop().getWorkshopId(): null,
                u.getPendingWorkshop() != null ? u.getPendingWorkshop().getWorkshopId(): null,
                u.getPendingWorkshop() != null ? u.getPendingWorkshop().getWorkshopName(): null
        );}
    }


