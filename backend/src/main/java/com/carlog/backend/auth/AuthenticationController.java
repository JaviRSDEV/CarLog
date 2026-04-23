package com.carlog.backend.auth;

import com.carlog.backend.security.JwtService;
import com.carlog.backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User user = authenticationService.register(registerRequest);
        String jwt = jwtService.generateToken(user);
        boolean remember = registerRequest.getRememberMe() != null && registerRequest.getRememberMe();
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(jwt, remember);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String jwt = jwtService.generateToken(user);
        boolean remember = (authRequest.getRememberMe() != null) && authRequest.getRememberMe();
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(jwt, remember);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = jwtService.cleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Sesión cerrada correctamente");
    }
}