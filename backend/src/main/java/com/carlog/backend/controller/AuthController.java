package com.carlog.backend.controller;

import com.carlog.backend.dto.ForgotPasswordDTO;
import com.carlog.backend.dto.ResetPasswordDTO;
import com.carlog.backend.service.PasswordRecoveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto){
        passwordRecoveryService.createPasswordResetToken(dto.email());

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO dto){
        passwordRecoveryService.resetPassword(dto.token(), dto.newPassword());
        return ResponseEntity.ok().build();
    }

}
