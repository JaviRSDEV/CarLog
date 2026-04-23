package com.carlog.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Value("${URL_CORS}")
    private String urlCors;

    @Bean
    @SuppressWarnings("java:S2637")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

            String csrfAttributeName = null;
            requestHandler.setCsrfRequestAttributeName(csrfAttributeName);

            http
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(csrf -> csrf
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                            .csrfTokenRequestHandler(requestHandler)
                            .ignoringRequestMatchers("/api/auth/**", "/ws-carlog/**")
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/api/auth/**", "/error").permitAll()
                            .requestMatchers("/uploads/**").authenticated()
                            .requestMatchers("/ws-carlog/**").authenticated()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").authenticated()
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();

        } catch (Exception e) {
            throw new org.springframework.beans.factory.BeanCreationException("Error al configurar el SecurityFilterChain", e);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(urlCors));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-XSRF-TOKEN"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
