package com.carlog.backend.DTO;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;

public record NewUserDTO(String dni,
                         String name,
                         String email,
                         String phone,
                         String password,
                         Role role,
                         Boolean mustChangePassword,
                         Long workShopId) {

    public static NewUserDTO of(User u){
        return new NewUserDTO(
                u.getDni(),
                u.getName(),
                u.getEmail(),
                u.getPhone(),
                u.getPassword(),
                u.getRole(),
                u.isMustChangePsswd(),
                u.getWorkShopId()
        );}
    }


