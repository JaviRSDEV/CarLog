package com.carlog.backend.auth;

import com.carlog.backend.model.User;
import com.carlog.backend.repository.UserJpaRepository;
import com.carlog.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        var user = User.builder()
                .dni(registerRequest.getDni())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        userJpaRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .userId(user.getDni())
                .workshopId(null)
                .workShop(null)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userJpaRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        String workShopName = null;
        Long workshopId = null;
        if(user.getWorkshop() != null){
            workShopName = user.getWorkshop().getWorkshopName();
            workshopId = user.getWorkshop().getWorkshopId();
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .userId(user.getDni())
                .workshopId(workshopId)
                .workShop(workShopName)
                .build();
    }
}
