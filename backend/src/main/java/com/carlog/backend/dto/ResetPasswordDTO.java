package com.carlog.backend.dto;

import com.carlog.backend.model.PasswordResetToken;

public record ResetPasswordDTO(
        String token,
        String newPassword
) {

    public static ResetPasswordDTO of(PasswordResetToken t){
        return new ResetPasswordDTO(
                t.getToken(),
                null
        );
    }
}
