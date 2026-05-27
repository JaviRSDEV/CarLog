package com.carlog.backend.dto;

import com.carlog.backend.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDTO(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email
) {
    public static ForgotPasswordDTO of(User u){
        return new ForgotPasswordDTO(
                u.getEmail()
        );
    }
}
