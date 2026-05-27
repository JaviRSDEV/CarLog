package com.carlog.backend.service;

import com.carlog.backend.error.InvalidRegistrationException;
import com.carlog.backend.error.RecoveryException;
import com.carlog.backend.model.PasswordResetToken;
import com.carlog.backend.model.User;
import com.carlog.backend.repository.PasswordResetTokenRepository;
import com.carlog.backend.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordRecoveryService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final SpringTemplateEngine springTemplateEngine;


    private final String recoveryUrl = "https://tallercarlog.com/reset-password?token=";

    @Transactional
    public void createPasswordResetToken(String email){
        Optional<User> userOpt = userJpaRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            log.warn("Solicitud de recuperación para email inexistente: {}", email);
            return;
        }

        User user = userOpt.get();

        passwordResetTokenRepository.deleteByUser(user);

        passwordResetTokenRepository.flush();

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(resetToken);

        String recoveryLink = this.recoveryUrl + token;

        Context context = new Context();
        context.setVariable("username", user.getName() != null ? user.getName() : user.getEmail());
        context.setVariable("recoveryLink", recoveryLink);

        try{
            String htmlContent = springTemplateEngine.process("emails/password-recovery", context);

            mailService.sendHtmlEmail(user.getEmail(), "Recuperación de contraseña - CarLog", htmlContent);
            log.info("Correo de recuperación enviado con éxito a: {}", email);
        }catch (Exception e){
            log.error("Error al procesar la plantilla o enviar el correo de recuperación: {}", e.getMessage());
            throw new RecoveryException("No se pudo procesar la solicitud de recuperación");
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRegistrationException("Token de recuperación no válido o inexistente"));

        if(resetToken.isExpired()){
            passwordResetTokenRepository.delete(resetToken);
            throw new InvalidRegistrationException("El enlace de recuperación ha expirado.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userJpaRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }


}
