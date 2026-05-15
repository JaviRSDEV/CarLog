package com.carlog.backend.config;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.dni}")
    private String adminDni;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.phone}")
    private String adminPhone;

    @Value("${admin.pass}")
    private String adminPass;

    @Override
    public void run(String... args){

        if(userJpaRepository.findByEmail(adminEmail).isEmpty()){
            log.info("Iniciando creación de cuenta de administración del sistema");

            User admin = User.builder()
                    .dni(adminDni)
                    .name(adminName)
                    .email(adminEmail)
                    .phone(adminPhone)
                    .password(passwordEncoder.encode(adminPass))
                    .role(Role.ADMIN)
                    .build();

            userJpaRepository.save(admin);
            log.info("Usuario administrador creado correctamente, usa el email: {}{}", adminEmail, "para acceder al panel de administración");
        }else{
            log.info("Usuario administrador ya existente");
        }
    }

}
