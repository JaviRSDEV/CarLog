package com.carlog.backend.auth;

import com.carlog.backend.model.Role;
import com.carlog.backend.model.User;
import com.carlog.backend.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest registerRequest) {
        Role requestedRole = registerRequest.getRole();
        if (requestedRole == null) {
            requestedRole = Role.CLIENT;
        }

        // Security: only CLIENT and DIY roles are allowed during self-registration.
        // MANAGER, CO_MANAGER and MECHANIC must be assigned by a workshop manager via invitation.
        if (requestedRole == Role.MANAGER || requestedRole == Role.CO_MANAGER || requestedRole == Role.MECHANIC) {
            requestedRole = Role.CLIENT;
        }

        var user = User.builder()
                .dni(registerRequest.getDni())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(requestedRole)
                .build();

        return userJpaRepository.save(user);
    }
}